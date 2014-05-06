package com.atlassian.maven.plugins.jgitflow.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.atlassian.jgitflow.core.JGitFlow;
import com.atlassian.jgitflow.core.exception.JGitFlowGitAPIException;
import com.atlassian.jgitflow.core.util.GitHelper;
import com.atlassian.maven.plugins.jgitflow.PrettyPrompter;
import com.atlassian.maven.plugins.jgitflow.ReleaseContext;
import com.atlassian.maven.plugins.jgitflow.VersionType;
import com.atlassian.maven.plugins.jgitflow.exception.JGitFlowReleaseException;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.util.ReleaseUtil;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.components.interactivity.PrompterException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.util.StringUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;

@Component(role = BranchLabelProvider.class)
public class DefaultBranchLabelProvider extends AbstractLogEnabled implements BranchLabelProvider
{
    @Requirement
    private VersionProvider versionProvider;
    
    @Requirement
    private PrettyPrompter prompter;

    @Override
    public String getVersionLabel(VersionType versionType, ProjectCacheKey cacheKey, ReleaseContext ctx, List<MavenProject> reactorProjects) throws JGitFlowReleaseException
    {
        Map<String, String> versions = versionProvider.getVersionsForType(versionType, cacheKey, reactorProjects, ctx);
        MavenProject rootProject = ReleaseUtil.getRootProject(reactorProjects);
        String rootProjectId = ArtifactUtils.versionlessKey(rootProject.getGroupId(), rootProject.getArtifactId());
        return versions.get(rootProjectId);
    }

    @Override
    public String getFeatureStartName(ReleaseContext ctx, JGitFlow flow) throws JGitFlowReleaseException
    {
        String featureName = StringUtils.defaultString(ctx.getDefaultFeatureName());

        if (ctx.isInteractive())
        {
            featureName = promptForFeatureName(flow.getFeatureBranchPrefix(), featureName);
        }
        else
        {
            if (StringUtils.isBlank(featureName))
            {
                throw new JGitFlowReleaseException("Missing featureName mojo option.");
            }
        }

        return featureName;
    }

    @Override
    public String getFeatureFinishName(ReleaseContext ctx, JGitFlow flow) throws JGitFlowReleaseException
    {
        String featureName = StringUtils.defaultString(ctx.getDefaultFeatureName());

        if (StringUtils.isBlank(featureName))
        {
            String currentBranch = null;

            try
            {
                currentBranch = flow.git().getRepository().getBranch();
                getLogger().debug("Current Branch is: " + currentBranch);
            }
            catch (IOException e)
            {
                throw new JGitFlowReleaseException(e);
            }

            getLogger().debug("Feature Prefix is: " + flow.getFeatureBranchPrefix());
            getLogger().debug("Branch starts with feature prefix?: " + currentBranch.startsWith(flow.getFeatureBranchPrefix()));
            if (currentBranch.startsWith(flow.getFeatureBranchPrefix()))
            {
                featureName = currentBranch.replaceFirst(flow.getFeatureBranchPrefix(), "");
            }
        }

        if (ctx.isInteractive())
        {
            List<String> possibleValues = new ArrayList<String>();
            if (null == featureName)
            {
                featureName = "";
            }

            try
            {
                String rheadPrefix = Constants.R_HEADS + flow.getFeatureBranchPrefix();
                List<Ref> branches = GitHelper.listBranchesWithPrefix(flow.git(), flow.getFeatureBranchPrefix(), flow.getReporter());

                for (Ref branch : branches)
                {
                    String simpleName = branch.getName().substring(branch.getName().indexOf(rheadPrefix) + rheadPrefix.length());
                    possibleValues.add(simpleName);
                }

                featureName = promptForExistingFeatureName(flow.getFeatureBranchPrefix(), featureName, possibleValues);
            }
            catch (JGitFlowGitAPIException e)
            {
                throw new JGitFlowReleaseException("Unable to determine feature names", e);
            }
        }
        else
        {
            if (StringUtils.isBlank(featureName))
            {
                throw new JGitFlowReleaseException("Missing featureName mojo option.");
            }
        }

        return featureName;
    }

    private String promptForFeatureName(String prefix, String defaultFeatureName) throws JGitFlowReleaseException
    {
        String message = "What is the feature branch name? " + prefix;
        String name = "";

        try
        {
            name = prompter.promptNotBlank(message, defaultFeatureName);
        }
        catch (PrompterException e)
        {
            throw new JGitFlowReleaseException("Error reading feature name from command line " + e.getMessage(), e);
        }

        return name;
    }

    private String promptForExistingFeatureName(String prefix, String defaultFeatureName, List<String> featureBranches) throws JGitFlowReleaseException
    {
        String message = "What is the feature branch name? " + prefix;

        String name = "";
        try
        {
            name = prompter.promptNumberedList(message, featureBranches, defaultFeatureName);
        }
        catch (PrompterException e)
        {
            throw new JGitFlowReleaseException("Error reading feature name from command line " + e.getMessage(), e);
        }

        return name;
    }
}
