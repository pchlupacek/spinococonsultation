package com.spinoco.consultation

import org.specs2.Specification
import org.specs2.specification.Fragments
import akka.actor.{ActorSystem, Props}
import org.specs2.matcher.MatchResult
import com.spinoco.consultation.external.{MakeCall, AddOperator, ConnectorActor}

/**
 * @author jakub.ryska
 *         Date: 8/2/13
 *         Time: 1:47 PM
 */
class SpinocoArchitectureSpec extends Specification {
  def is: Fragments =
    s2"""
      Client should make a call through connector to the Gateway $clientRequestToGateway.
      Gateway should notify the client
    """

  def clientRequestToGateway:MatchResult[Any] = {
    val as = ActorSystem("testActorSystem")
    val connector = as.actorOf(Props[ConnectorActor])
    val operator = as.actorOf(Props[OperatorActor])
    connector ! AddOperator("op1", operator)
    connector ! MakeCall("op1", "777 123 456")
    ???
  }
}
