package com.tassiovirginio.jnose.plugin;

import br.ufba.jnose.core.Config;
import br.ufba.jnose.core.JNoseCore;
import br.ufba.jnose.dto.TestClass;
import br.ufba.jnose.dto.TestSmell;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;

public class JnosePluginBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private boolean useFrench;

    @DataBoundConstructor
    public JnosePluginBuilder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        if (useFrench) {
            listener.getLogger().println("Bonjour, " + name + "!");
        } else {
            listener.getLogger().println("Procurando TestSmells, " + name + "!");
        }

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
        List<TestClass> lista = jNoseCore.getFilesTest(workspace.getRemote());

        Boolean primeiraLinha = true;

        for(TestClass testClass : lista){
            listener.getLogger().println("class-test: " + testClass.getName() + "\t\t\t junit-version: " + testClass.getJunitVersion() + "\t\t\t qtd-test-smells:" + testClass.getListTestSmell().size());
            List<TestSmell> listaTestSmells = testClass.getListTestSmell();
            primeiraLinha = true;

            for(TestSmell testSmell : listaTestSmells){
                if(primeiraLinha){
                    listener.getLogger().println("\t\t\t TestSmell \t\t\t Method \t\t\t Range ");
                    primeiraLinha = false;
                }
                listener.getLogger().println("\t\t\t" + testSmell.getName() + "\t\t\t" + testSmell.getMethod() + "\t\t\t" + testSmell.getRange());
            }
        }
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_tooShort());
            if (!useFrench && value.matches(".*[éáàç].*")) {
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_reallyFrench());
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }

    }
}


