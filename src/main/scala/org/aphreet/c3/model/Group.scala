package org.aphreet.c3.model

import net.liftweb.mapper._
import net.liftweb.util.FieldError
import xml.Text
import net.liftweb.common.Box
import net.liftweb.http.SHtml

/**
 * Copyright (c) 2011, Dmitry Ivanov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *

 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the IFMO nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
 

class Group extends LongKeyedMapper[Group] with IdPK {

  thisgroup =>

  def getSingleton = Group

  object owner extends MappedLongForeignKey(this,User){
    override def toForm = Box(SHtml.selectObj[User](User.findAll.map(user => (user,user.email.is)),User.currentUser, usr => thisgroup.owner(usr)))
  }

  object name extends MappedString(this,64){

    def isUnique(s: String): List[FieldError] = {
      if(!Group.find(By(Group.name,s)).isEmpty) List(FieldError(this,Text("Group with this name already exists")))
      else Nil
    }

    override def validations = isUnique _ :: super.validations
  }


}

object Group extends Group with LongKeyedMetaMapper[Group] {

  override def dbTableName = "groups"

  override def fieldOrder = name :: Nil


  //object users extends HasManyThrough[Group,User,UserGroup,_](this, User, UserGroup, UserGroup.user, UserGroup.group)

}