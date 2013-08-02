package com.spinoco.consultation

import org.specs2.Specification
import org.specs2.specification.Fragments

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

  def clientRequestToGateway = {
    ???
  }
}
