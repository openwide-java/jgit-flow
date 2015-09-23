package com.atlassian.maven.plugins.jgitflow.extension.command.external;

import com.atlassian.jgitflow.core.BranchType;
import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.jgitflow.api.StartBranchExtension;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

import org.codehaus.plexus.component.annotations.Component;

@Component(role = StartBranchExternalExecutor.class)
public class StartBranchExternalExecutor extends CachedVersionExternalExecutor
{
    @Override
    public void execute(MavenJGitFlowExtension extension, String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException
    {
        if (null == extension || !StartBranchExtension.class.isAssignableFrom(extension.getClass()))
        {
            return;
        }

        StartBranchExtension startExtension = (StartBranchExtension) extension;
        try
        {
            startExtension.onTopicBranchVersionChange(newVersion, oldVersion, flow);
        }
        catch (Exception e)
        {
            throw new MavenJGitFlowExtensionException("Error running external extension", e);
        }
    }
}
