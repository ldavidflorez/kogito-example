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
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class EmailService {

    @Inject
    @RestClient
    EmailGateway emailGateway;

    public boolean proccess(Candidate candidate, Boolean checkStatus) {
        EmailNotification emailNotification = new EmailNotification();

        emailNotification.setToEmail(candidate.getEmail());

        if (checkStatus != null && checkStatus) {
            emailNotification.setSubject("Felicidades, pasas el primer filtro!");
            emailNotification.setBody("Hola " + candidate.getName() + ".\n\nTe comentamos que pasas a la siguiente fase del proceso de selección para el cargo " + candidate.getJobPosition()
                    + ".\n\nRecuerda estar atento a tus medios de contacto. Feliz día.");
        } else {
            emailNotification.setSubject("Lo sentimos. No fuiste seleccionado.");
            emailNotification.setBody("Hola " + candidate.getName() + ".\n\nTe comentamos que esta vez no continuas el proceso de selección para el cargo " + candidate.getJobPosition()
                    + ".\n\nRecuerda seguir aplicando. Feliz día.");
        }

        Response response = emailGateway.sendEmail(emailNotification);

        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
