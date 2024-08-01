/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.hr;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CheckRequirements {
    public boolean check(Candidate candidate) {
        System.out.println("Checking " + candidate.getName() + " skills...");

        String candidateSkills = candidate.getSkills().toLowerCase();
        int candidateSalary = candidate.getSalary();

        if (candidateSkills.contains("python") && candidateSkills.contains("java") && candidateSalary <= 3000) {
            return true;
        }

        return false;
    }
}
