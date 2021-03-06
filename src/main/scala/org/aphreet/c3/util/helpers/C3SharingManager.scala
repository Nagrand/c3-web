/**
 * Copyright (c) 2011, Mikhail Malygin
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
package org.aphreet.c3.util.helpers

import org.aphreet.c3.lib.DependencyFactory._
import org.aphreet.c3.model.C3Path
import net.liftweb.common._
import net.liftweb.sitemap.Loc.Link
import xml.NodeSeq
import net.liftweb.util.Helpers._
import net.liftweb.http.S
import com.ifunsoftware.c3.access.fs.{ C3FileSystemNode, C3File }
import org.aphreet.c3.lib.metadata.Metadata
import Metadata._
import net.liftweb.util.PassThru
import net.liftweb.sitemap.Menu
import net.liftweb.http.js.JsCmd
import com.ifunsoftware.c3.access.{ StringMetadataValue, C3System }
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.common.Full
import org.aphreet.c3.snippet.LiftMessages
/**
 * @author  a-legotin on 4/26/2014.
 */
object C3SharingManager {

  def checkFile(groupId: String, path: List[String], extension: String) = {
    val correctPath:List[String] = path.last match{
      case "index" => path.take(path.length - 1)
      case _ => path
    }
    val c3 = inject[C3System].open_!
    val file = tryo{c3.getFile(C3Path(groupId, correctPath, extension))}
    file match {
      case Full(f:C3FileSystemNode) => checkAccess(f.asFile)
      case _ => {S.notice("Failed")
        S.redirectTo("/")}
    }
  }

  def checkAccess(file: C3File): Nothing = {
    val metadata = file.metadata
    val id = S.param("id").openOr("")
    if (metadata.get(HASH).getOrElse("") == id && id.length != 0)
      S.redirectTo("/groups" + file.fullname)
    else {
      S.notice(S.?("access.restricted"))
      S.redirectTo("/")
    }
  }
}
