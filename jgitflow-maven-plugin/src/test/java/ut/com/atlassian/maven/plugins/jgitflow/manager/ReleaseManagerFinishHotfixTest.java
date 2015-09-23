package ut.com.atlassian.maven.plugins.jgitflow.manager;

import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.jgitflow.core.JGitFlowInitCommand;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.helper.JGitFlowSetupHelper;
import com.atlassian.maven.plugins.jgitflow.helper.ProjectHelper;
import com.atlassian.maven.plugins.jgitflow.manager.FlowReleaseManager;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.util.FileUtils;
import org.eclipse.jgit.api.Git;
import org.junit.Test;
import ut.com.atlassian.maven.plugins.jgitflow.testutils.RepoUtil;

import java.io.File;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReleaseManagerFinishHotfixTest extends AbstractFlowManagerTest
{
    @Test
    public void hotfixBasicPomWithOnlyRemoteReleaseBranch() throws Exception
    {
        ProjectHelper projectHelper = (ProjectHelper) lookup(ProjectHelper.class.getName());
        JGitFlowSetupHelper setupHelper = (JGitFlowSetupHelper) lookup(JGitFlowSetupHelper.class.getName());

        Git git = null;
        Git remoteGit = null;

        List<MavenProject> remoteProjects = createReactorProjects("remote-master-project", null);

        File master = remoteProjects.get(0).getBasedir();

        //make sure we're clean
        File remoteGitDir = new File(master, ".git");
        if (remoteGitDir.exists())
        {
            FileUtils.cleanDirectory(remoteGitDir);
        }

        remoteGit = RepoUtil.createRepositoryWithMasterAndDevelopAndRelease(master, "1.0");
        remoteGit.add().addFilepattern(".").call();
        remoteGit.commit().setMessage("pom commit").call();

        File localProject = new File(testFileBase, "projects/local/local-git-project");
        git = Git.cloneRepository().setDirectory(localProject).setURI("file://" + remoteGit.getRepository().getWorkTree().getPath()).call();

        List<MavenProject> projects = createReactorProjects("remote-develop-project", "local/local-git-project", null, false);
        File projectRoot = projects.get(0).getBasedir();

        MavenSession session = new MavenSession(getContainer(), new Settings(), localRepository, null, null, null, projectRoot.getAbsolutePath(), new Properties(), new Properties(), null);

        JGitFlowInitCommand initCommand = new JGitFlowInitCommand();
        JGitFlow flow = initCommand.setDirectory(git.getRepository().getWorkTree()).call();
        flow.git().add().addFilepattern(".").call();
        flow.git().commit().setMessage("updated develop pom").call();

        ReleaseContext ctx = new ReleaseContext(projectRoot);
        ctx.setInteractive(false).setNoBuild(true).setPushHotfixes(true);

        FlowReleaseManager relman = getHotfixManager();

        relman.start(ctx, projects, session);

        assertEquals(flow.getHotfixBranchPrefix() + "1.0.1", git.getRepository().getBranch());
        //add a file to hotfix
        File junkFile = new File(git.getRepository().getWorkTree(), "junk.txt");
        Files.write("I am Junk", junkFile, Charsets.UTF_8);

        JGitFlow newFlow = relman.flow();
        newFlow.git().add().addFilepattern(".").call();
        newFlow.git().commit().setMessage("committing junk file").call();

        //delete the local release branch
//        git.checkout().setName("develop").call();
//        git.branchDelete().setBranchNames(flow.getReleaseBranchPrefix() + "1.0").setForce(true).call();

        relman.finish(ctx, projects, session);

        assertOnDevelop(flow);
        assertTrue(FileUtils.fileExists(junkFile.getAbsolutePath()));
        git.checkout().setName("release/1.0").call();
        assertOnRelease(flow,"1.0");
        assertTrue(FileUtils.fileExists(junkFile.getAbsolutePath()));

    }
}
