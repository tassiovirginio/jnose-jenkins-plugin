package com.tassiovirginio.jnose.plugin;

import br.ufba.jnose.core.Config;
import br.ufba.jnose.core.JNoseCore;
import br.ufba.jnose.dto.TestClass;
import br.ufba.jnose.dto.TestSmell;
import hudson.model.Action;
import hudson.model.Run;
import jenkins.model.Jenkins;
import jenkins.model.RunAction2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JNoseAction implements RunAction2 {

    private String projectPath;

    private String qtdTestSmells;

    private String projectName;

    private List<String[]> results;

    private transient Run run;

    public JNoseAction(String projectPath) {
        this.projectPath = projectPath;

        this.results = new ArrayList<>();

        Config conf = new Config() {
            public Boolean assertionRoulette() {
                return true;
            }
            public Boolean conditionalTestLogic() {
                return true;
            }
            public Boolean constructorInitialization() {
                return true;
            }
            public Boolean defaultTest() {
                return true;
            }
            public Boolean dependentTest() {
                return true;
            }
            public Boolean duplicateAssert() {
                return true;
            }
            public Boolean eagerTest() {
                return true;
            }
            public Boolean emptyTest() {
                return true;
            }
            public Boolean exceptionCatchingThrowing() {
                return true;
            }
            public Boolean generalFixture() {
                return true;
            }
            public Boolean mysteryGuest() {
                return true;
            }
            public Boolean printStatement() {
                return true;
            }
            public Boolean redundantAssertion() {
                return true;
            }
            public Boolean sensitiveEquality() {
                return true;
            }
            public Boolean verboseTest() {
                return true;
            }
            public Boolean sleepyTest() {
                return true;
            }
            public Boolean lazyTest() {
                return true;
            }
            public Boolean unknownTest() {
                return true;
            }
            public Boolean ignoredTest() {
                return true;
            }
            public Boolean resourceOptimism() {
                return true;
            }
            public Boolean magicNumberTest() {
                return true;
            }
            public Integer maxStatements() {
                return 30;
            }
        };

        JNoseCore jNoseCore = new JNoseCore(conf);
        List<TestClass> lista = null;
        try {
            lista = jNoseCore.getFilesTest(projectPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Boolean primeiraLinha = true;

        for(TestClass testClass : lista){
//            System.out.println("class-test: " + testClass.getName() + "\t\t\t junit-version: " + testClass.getJunitVersion() + "\t\t\t qtd-test-smells:" + testClass.getListTestSmell().size());
            List<TestSmell> listaTestSmells = testClass.getListTestSmell();

            if(primeiraLinha) {
                this.projectName = testClass.getProjectName();
                primeiraLinha = false;
            }

            for(TestSmell testSmell : listaTestSmells){
//                if(primeiraLinha){
//                    System.out.println("\t\t\t TestSmell \t\t\t Method \t\t\t Range ");
//                    primeiraLinha = false;
//                }
//                System.out.println("\t\t\t" + testSmell.getName() + "\t\t\t" + testSmell.getMethod() + "\t\t\t" + testSmell.getRange());
                String[] line = new String[4];
                line[0] = testClass.getName();
                line[1] = testSmell.getMethod();
                line[2] = testSmell.getName();
                line[3] = testSmell.getRange();

                results.add(line);

            }
        }


    }

    public String getProjectName(){
        return this.projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getQtdTestSmells(){
        return qtdTestSmells;
    }

    public List<String[]> getResults(){
        return results;
    }

    @Override
    public String getIconFileName() {
//        return "document.png";
//        return (Jenkins.RESOURCE_PATH + "/logo.png").replaceFirst("^/", "");
        return Jenkins.RESOURCE_PATH + "/plugin/jnose-jenkins/img/logo.png";
    }

    @Override
    public String getDisplayName() {
        return "JNose";
    }

    @Override
    public String getUrlName() {
        return "jnose";
    }

    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run;
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run;
    }

    public Run getRun() {
        return run;
    }
}
