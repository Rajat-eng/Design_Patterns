package com.linkedin;

import com.linkedin.Facade.LinkedInFacade;

public class Main {
    public static void main(String[] args) {
        LinkedInFacade linkedInFacade = new LinkedInFacade();
        try {
            linkedInFacade.runDemoScenario();
        } finally {
            linkedInFacade.shutdown();
        }
    }
}