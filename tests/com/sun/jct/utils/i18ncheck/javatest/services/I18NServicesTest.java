/*
 * $Id$
 *
 * Copyright (c) 2006, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.sun.jct.utils.i18ncheck.javatest.services;

import com.sun.javatest.services.ServiceManager.ServiceCommandManager;
import java.awt.EventQueue;


public class I18NServicesTest
{
    public static void main(final String[] args) {
        try {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new I18NServicesTest().run(args);
                    System.exit(0);
                }
            });

        }
        catch (Throwable t) {
            t.printStackTrace();
            System.exit(2);
        }
    }

    void run(String[] args) {
        ServiceCommandManager mgr = new ServiceCommandManager();
        mgr.getHelp();
    }
}
