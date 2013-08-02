package com.spinoco.consultation

import akka.actor.{ActorRef, Actor}
import scala.collection.mutable

case class AddOperator(id:String, operator:ActorRef)

case class MakeCall(operatorId:String, number:String)

/**
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:45 PM
 */
class ConnectorActor extends Actor {

  val operators : mutable.Map[String, ActorRef] = mutable.Map()

  def receive: Actor.Receive = {
    case AddOperator(id, operator) =>
      operators += (id -> operator)
    case makeCall @ MakeCall(number, operatorId) =>
      operators(number) ! makeCall
  }
}
