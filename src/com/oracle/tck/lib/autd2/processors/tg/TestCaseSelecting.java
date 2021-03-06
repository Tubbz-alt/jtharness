/*
 * $Id$
 *
 * Copyright (c) 2001, 2020, Oracle and/or its affiliates. All rights reserved.
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

package com.oracle.tck.lib.autd2.processors.tg;

import com.oracle.tck.lib.autd2.TestCaseContext;
import com.oracle.tck.lib.autd2.TestGroupContext;
import com.oracle.tck.lib.autd2.AUTD2Utils;
import com.oracle.tck.lib.autd2.processors.InterestedInAnnotations;
import com.oracle.tck.lib.autd2.processors.Processor;
import com.sun.tck.lib.tgf.ReflectionUtils;
import com.sun.tck.test.TestCase;
import com.sun.tck.test.TestGroup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Processor recognises methods annotated with <code>@TestCase</code>
 * annotations, creates testcase contexts basing on the found methods
 * and adds created contexts to the parent testgroup context.
 */
@InterestedInAnnotations(TestGroup.class)
public class TestCaseSelecting extends Processor.TestGroupProcessor {

    /**
     * {@inheritDoc}
     */
    public void process(TestGroupContext.TestGroupLifePhase lifePhase,
                        TestGroupContext tgContext) {
        Object testGroupInstance = tgContext.getTestGroupInstance();
        Class<?> testGroupClass = testGroupInstance.getClass();

        final List<Class<?>> hierarchy = ReflectionUtils.getClassHierarchy(testGroupClass);
        for (Class<?> clazz : hierarchy) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getAnnotation(testCaseMarkerAnnotation()) != null) {
                    TestCaseContext tcContext = new TestCaseContext(tgContext);
                    method.setAccessible(true);
                    tcContext.setTestCaseMethod(method);
                    tcContext.setParentContext(tgContext);

                    Set<Processor<?,?>> usedProcessors = tgContext.getAllUsedProcessors();

                    Map<TestCaseContext.TestCaseLifePhase, List<TestCaseProcessor>>
                            proc2LifePhaseForTheTestCaseMethod =
                            AUTD2Utils.getProc2LifePhaseForTheTestCaseMethod(method, usedProcessors);
                    tcContext.setLifePhase2ProcessorsMap(proc2LifePhaseForTheTestCaseMethod);
                    tgContext.addTestCaseContext(tcContext);
                }
            }
        }
    }

    protected Class<? extends Annotation> testCaseMarkerAnnotation() {
        return TestCase.class;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public TestGroupContext.TestGroupLifePhase[] getLifePhasesInterestedIn() {
        return new TestGroupContext.TestGroupLifePhase[] {TestGroupContext.TestGroupLifePhase.TESTCASE_ADDING};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseOwnership getPhaseOwnership(TestGroupContext.TestGroupLifePhase interestedLifePhase) {
        return PhaseOwnership.MANY_PER_PHASE;
    }

}
