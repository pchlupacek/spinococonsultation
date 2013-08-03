package com.spinoco.consultation.external

import akka.actor.{ActorRef, Actor}
import scala.collection.mutable

case class AddOperator(id:String, operator:ActorRef)

case class MakeCall(operatorId:String, number:String)

/**
 *  Connector Actor represent some sort of client connection, i.e. WebSocket 
 *  Its sole role is to get message from protocol, then serialize it and forward it to 
 *  Gateway Actor. 
 *  
 *  We can maybe see it as sort of TCP Socket, NIO Backed.
 * 
 * 
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:45 PM
 */
class ConnectorActor extends Actor {
  def receive = ???
}
