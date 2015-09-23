package com.atlassian.maven.plugins.jgitflow.extension;

import com.atlassian.jgitflow.core.extension.FeatureStartExtension;
import com.atlassian.jgitflow.core.extension.impl.EmptyBranchCreatingExtension;
import com.atlassian.maven.jgitflow.api.MavenJGitFlowExtension;
import com.atlassian.maven.plugins.jgitflow.extension.command.CacheVersionsCommand;
import com.atlassian.maven.plugins.jgitflow.extension.command.UpdateFeaturePomsWithSnapshotsCommand;

import com.atlassian.maven.plugins.jgitflow.extension.command.external.StartBranchExternalExecutor;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role = FeatureStartPluginExtension.class)
public class FeatureStartPluginExtension  extends EmptyBranchCreatingExtension implements ExternalInitializingExtension, FeatureStartExtension
{

    @Requirement
    private UpdateFeaturePomsWithSnapshotsCommand updateFeaturePomsWithSnapshotsCommand;

    @Requirement
    protected CacheVersionsCommand cacheVersionsCommand;

    @Requirement
    protected StartBranchExternalExecutor startBranchExecutor;

    @Override
    public void init(MavenJGitFlowExtension externalExtension)
    {
        startBranchExecutor.init(externalExtension);

        addAfterCreateBranchCommands(
                cacheVersionsCommand
                , updateFeaturePomsWithSnapshotsCommand
                , startBranchExecutor
        );
    }
}
