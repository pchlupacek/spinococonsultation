spinococonsultation
===================


This repo contains basic sample of communication model implemented with Actors. 
Idea is to replace as much as possible the Non-typesafety and composability of actor based
solution with Streams. 

The communication workflow is as follows:

```
+-----------------+          +-----------------+           +-----------------+             +----------------+
|                 |  ====>   |                 | ======>   |                 |  ========>  |                |
|    Connector    |          |     Operator    |           |    Gateway      |             |  External S.   |
|                 |  <====   |                 | <======   |                 |  <========  |                |
+-----------------+          +-----------------+           +-----------------+             +----------------+

```


The idea is to model at minimum at least Operator and gateway as Pure processes , and ancapsulate Connector 
and External System in Processes

The important part is that Operator holds that state that is updated in both directions. 

Aditionally Gateway will server multiple Operators, that are losely coupled with Gateway (they may appear/dissapear depending
on client connection from connector. 


