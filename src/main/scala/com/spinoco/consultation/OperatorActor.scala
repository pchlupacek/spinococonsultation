package com.spinoco.consultation

import akka.actor.{ActorRef, Actor}


/**
 *
 * There may be one or more "Operator" Actors. Operator actos keeps state that is mixed from 
 * Connector and Gateway. For simplicity let's say is tupple of ints, where one is comming from Gateway and other comes
 * from Connector. 
 *
 * Operator Actor is also responsible (based on its state) to send updates to connector and requests(updates) to gateway that 
 * forwards them to External system
 *
 * i.e. 
 *
 * {{{
 *  Dial:
 *
 *  Connector ->  Operator ->  (may dial)    -> Gateway Actor
 *                         \-> (cannot dial) -> Connector (with Nack)
 *
 *  CallUpdate:
 *
 *  GatewayActor -> Operator -> (calculate state) ->  Connector
 *
 * }}}
 *
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:45 PM
 */
class OperatorActor(connector: ActorRef, gateway:ActorRef) extends Actor {

  var status: (Int, Long) = (0, 0)
 

  def receive = {
    
    // Gateway >> Operator >> Connector
    case UpdateOperator(s) =>
      status = (s, status._2)
      connector ! UpdateConnector(status)

    // Connector >> Operator  
    case FromConnector(l) if l <= 100L =>
      status = (status._1, l)
      sender ! Ack

    // Connector >> Operator (NACK)  
    case FromConnector(l) if l <= 200L =>
      sender ! Nack

    // Connector >> Operator >> Gateway  
    case FromConnector(l) =>
      gateway ! FromConnector(l)
  }
}


case class UpdateConnector(s: (Int, Long))

case class FromConnector(l: Long)

case object Ack

case object Nack

