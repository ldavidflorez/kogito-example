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

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.oracle.svm.core.annotate.Inject;

@ApplicationScoped
public class DecisionService {
    @Inject
    @RestClient
    EmailGateway emailGateway;

    public void proccess(boolean hrApproval, boolean itApproval, Candidate candidate) {
        EmailNotification emailNotification = new EmailNotification();

        emailNotification.setToEmail(candidate.getEmail());
        emailNotification.setSubject("Resultados proceso de selección " + candidate.getJobPosition());

        if (hrApproval && itApproval) {
            emailNotification.setBody("Felicitaciones, el puesto es tuyo!!!\n\n" + candidate.getName()
                    + ", Recuerda estar pendiente de tus medios de contacto para continuar con la contratación.\n\nQue tengas un excelente resto de día!");
        } else {
            emailNotification.setBody("Hola " + candidate.getName()
                    + ".\n\nTe comentamos que esta vez no continuas el proceso de selección para el cargo "
                    + candidate.getJobPosition()
                    + ".\n\nRecuerda seguir aplicando. Feliz día.");
        }

        emailGateway.sendEmail(emailNotification);
    }
}
