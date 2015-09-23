package ut.com.atlassian.maven.plugins.jgitflow;

import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.MavenFeatureStartExtension;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

public class TestFeatureStartExtension extends NoopBranchCreateExtension implements MavenFeatureStartExtension
{
    private String oldVersion;
    private String newVersion;
    
    @Override
    public void onTopicBranchVersionChange(String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public String getOldVersion() {
        return oldVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }
}
