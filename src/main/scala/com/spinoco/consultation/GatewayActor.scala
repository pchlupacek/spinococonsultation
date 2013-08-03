package com.spinoco.consultation

import akka.actor.{Terminated, ActorRef, Actor}
import com.spinoco.consultation.external.FromExternal
import com.spinoco.consultation.external.ToExternal
import concurrent.duration._

/**
 *
 * This class is simulating gateway to some external system. 
 * Its purpose is to semi-synchronize state with external system and push changes back to 
 * Operator Actor. 
 *
 * Additionally when the OperatorActor wishes to send request to the external system, 
 * GatewayActor is responsible for dispatching it to external system. 
 *
 * External systems may be 2 or more systems working in active-standby pattern, gateway
 * should assure that only one system is used at a time and once that fails it should 
 * make sure that the external standby system is used. Eventually when standby system fails
 * It should try again try that primary system and if not available it should try each given timeout to retry
 * Either primary or secondary system, for example in cyclic pattern
 *
 * For simplicity lets assume that there are only 2 systems (active + standby)
 *
 * There can be one ore more listeners that should receive updates from external system.
 *
 * 
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:45 PM
 */
class GatewayActor(externalSystems: Seq[ActorRef]) extends Actor {

  import context.dispatcher
  
  var soFarCounted: Int = 0

  var lastFailed: Option[ActorRef] = None

  var active: Option[ActorRef] = None

  var listeners: Set[ActorRef] = Set()


  override def preStart() {
    self ! TryConnectToES
  }

  def receive = {

    case TryConnectToES =>
      externalSystems.diff(lastFailed.toSeq).head ! ToExternal("#Connect")


    //Notification that external system failed, i.e. got disconnected
    case FromExternal("#Failed") =>
      lastFailed = Some(sender)
      active = None
      //potentially notification to listeners that data may be stale
      context.system.scheduler.scheduleOnce(10 seconds, self, TryConnectToES) //tryConnect again after 10s


    // Data received from external system  
    case esd@FromExternal(data) =>
      soFarCounted = data.size
      listeners.foreach(_ ! UpdateOperator(soFarCounted))

    //Data Supposed to be sent to external system  
    case FromConnector(msg) =>
      active match {
        case Some(es) => es ! ToExternal(msg.toString)
        case None => sender ! Nack  //may require this Nack
      }

    //Register Listener
    case Register(listener) =>
      listeners = listeners + listener
      //potentially notify listeners that new listener appear
      context.watch(listener)

    case Terminated(listener) =>
      listeners = listeners - listener
    //potentially notify listeners that listener disapeared

  }
}


case object TryConnectToES

case class Register(listener: ActorRef)

case class UpdateOperator(i:Int)

