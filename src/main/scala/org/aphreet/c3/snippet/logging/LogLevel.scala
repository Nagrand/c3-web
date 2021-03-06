package org.aphreet.c3.snippet.logging

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

import net.liftmodules.widgets.logchanger._
import net.liftweb.sitemap.Loc
import org.aphreet.c3.model.User
import net.liftweb.util.Props
import net.liftweb.common.Full

object LogLevel extends LogLevelChanger with LogbackLoggingBackend {

  override def menuLocParams: List[Loc.AnyLocParam] = if (User.loggedIn_?) {
    if (Props.productionMode)
      List(User.testSuperUser)
    else Nil // in production mode only super users have access to logging management
  } else Nil // In all modes only authenticated users can see the menu

  override def screenWrap = Full(

    <lift:surround with="default">
      <lift:bind-at name="content-header">
        Log manager
      </lift:bind-at>
      <lift:bind-at name="content">
        <div>
          <lift:bind/>
        </div>
      </lift:bind-at>
      <!--
        <lift:bind-at name="right-panel-content">
            <div class="right-panel-content">
                <div class="right-panel-section right-panel-section-first">

                </div>
                <div class="right-panel-section">

                </div>
            </div>
        </lift:bind-at>
        -->
    </lift:surround>)

}

