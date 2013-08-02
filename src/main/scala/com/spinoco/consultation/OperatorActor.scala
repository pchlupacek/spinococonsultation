package com.spinoco.consultation

import akka.actor.Actor

trait Status

case object Ready extends Status

case object Break extends Status

/**
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:45 PM
 */
class OperatorActor extends Actor {

  var status: Status = Ready

  def receive: Actor.Receive = {
    case s:Status => status = s
  }
}
