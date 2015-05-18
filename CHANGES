### Version 1.0-m5.1 
(18/May/2015)
#### Bug
* [MJF-228](https://ecosystem.atlassian.net/browse/MJF-228) - pushes fail if remote sends back *any* messages

### Version 1.0-m5 
(14/May/2015)
#### Bug
* [MJF-118](https://ecosystem.atlassian.net/browse/MJF-118) - start-release fails with ```org.eclipse.jgit.errors.TransportException: ssh://...: Auth fail```
* [MJF-134](https://ecosystem.atlassian.net/browse/MJF-134) - Error if ```<artifactId>``` not at top of POM
* [MJF-178](https://ecosystem.atlassian.net/browse/MJF-178) - feature-finish uses release profile
* [MJF-179](https://ecosystem.atlassian.net/browse/MJF-179) - Snapshot dependencies hard to find when detected on release-start
* [MJF-187](https://ecosystem.atlassian.net/browse/MJF-187) - NullPointerException on release-start
* [MJF-191](https://ecosystem.atlassian.net/browse/MJF-191) - release-start proclaiming success despite underlying error
* [MJF-193](https://ecosystem.atlassian.net/browse/MJF-193) - featureRebase=True leads to "Parameter "upstream" is missing"
* [MJF-194](https://ecosystem.atlassian.net/browse/MJF-194) - Error starting feature: Error verifying initial version state in poms: Cannot start a feature due to snapshot dependencies
* [MJF-197](https://ecosystem.atlassian.net/browse/MJF-197) - Build fails if scmCommentPrefix has space in it
* [MJF-198](https://ecosystem.atlassian.net/browse/MJF-198) - scmCommentPrefix value is not used for merge messages for release-finish goal
* [MJF-204](https://ecosystem.atlassian.net/browse/MJF-204) - Unable to merge on release/hotfix finish if develop has a new module
* [MJF-206](https://ecosystem.atlassian.net/browse/MJF-206) - UpdatePomsWithNonSnapshotCommand hides exception information
* [MJF-207](https://ecosystem.atlassian.net/browse/MJF-207) - hotfix-finish fails if keepBranch=false and release branch present
* [MJF-208](https://ecosystem.atlassian.net/browse/MJF-208) - release-finish fails after hotfix-finish
* [MJF-210](https://ecosystem.atlassian.net/browse/MJF-210) - UpdatePomsWithSnapshotsCommand commit message is misleading
* [MJF-227](https://ecosystem.atlassian.net/browse/MJF-227) - Generate Documentation using Maven Reflow
#### Improvement
* [MJF-81](https://ecosystem.atlassian.net/browse/MJF-81) - Use commit prefix for merge commit message
* [MJF-165](https://ecosystem.atlassian.net/browse/MJF-165) - hotfix finish to back merge into release branch
* [MJF-176](https://ecosystem.atlassian.net/browse/MJF-176) - Calculate next development version based on overridden release version if specified
* [MJF-183](https://ecosystem.atlassian.net/browse/MJF-183) - Choose EOL for pom updates
* [MJF-214](https://ecosystem.atlassian.net/browse/MJF-214) - Add a arguments property on goal release-finish to pass additional arguments to pass to the Maven executions.
* [MJF-215](https://ecosystem.atlassian.net/browse/MJF-215) - Add a goals property on release-finish mojo for goals to execute

Version 1.0-m2

** Bug
    * [MJF-84] - Release finish fails with merge errors
    * [MJF-93] - release-finish does not commit updated snapshot versions on develop
    * [MJF-103] - Finishing of feature doesn't pushes merging back into develop and the deletion of feature branch
    * [MJF-151] - Error configuring remote git repo with url with enableSsAgent true on Mac OS
    * [MJF-155] - RequirementHelper.requireNoExistingReleaseBranches ignores releaseBranchPrefix
    * [MJF-156] - BranchHelper has a regression in getCurrentBranchType

** Epic
    * [MJF-146] - Refactor JGit Flow to use Command Pattern for hooking into lifecycle

** Improvement
    * [MJF-67] - Develop version on release
    * [MJF-91] - Option to update develop version at release-start rather than release-end
    * [MJF-150] - Refactor core commands to remove code duplication

** New Feature
    * [MJF-115] - Check for 'core.autocrlf' being set to false.
    * [MJF-116] - Add support for 'core.eol' GIT configuration parameter
    * [MJF-147] - Implement command hooks in jgit flow library
    * [MJF-148] - Implement pom updates as jgit flow commands
    * [MJF-149] - Expose a way to provide external jgit flow commands to the maven plugin

=======================

Version 1.0-m1

** Bug
    * [MJF-83] - Prompting doesn't work under Cygwin
    * [MJF-88] - java.lang.StackOverflowError while using Plugin inside IDE (Intellij)
    * [MJF-89] - release-start does not change version in parent or submodules
    * [MJF-95] - Merge explosion, 2!=200?
    * [MJF-96] - release-start does not push branch from Bamboo to Stash.
    * [MJF-104] - jgit-flow-core versions 0.20 and 0.21 missing POM in repository
    * [MJF-105] - Cannot release - unstaged changes on a clean tree
    * [MJF-107] - gitflow:hotfix-finish - after merge pom.xml is not set back to the development version
    * [MJF-108] - hotfix-start - you cannot overwrite releaseVersion via --define option
    * [MJF-111] - If module references relative path, the pom for that module is not committed.
    * [MJF-112] - Confusing 'API incompatibility was encountered' message when POM file is corrupt
    * [MJF-114] - hotfix-start does not consume documented 'releaseVersion' parameter
    * [MJF-117] - start-release fails with org.eclipse.jgit.errors.TransportException: Nothing to fetch
    * [MJF-119] - Feature Finish reporting merge conflicts when none exist.
    * [MJF-125] - hotfix-finish does not push develop branch with the final developmentVersion
    * [MJF-127] - Rename maven-jgitflow-plugin to jgitflow-maven-plugin
    * [MJF-136] - jgitflow:release-Start failed in Jenkins with error message - error configuring remote git repo with url: null: Nothing to fetch.
    * [MJF-138] - Hotfix release is tagged on a hotfix branch, not on master

** Improvement
    * [MJF-13] - Rename maven-jgitflow-plugin to jgitflow-maven-plugin
    * [MJF-135] - Add flag to always set origin url
    * [MJF-141] - Add scmCommentSuffix
    * [MJF-142] - Parameters to specify user/pass for ssh
    * [MJF-143] - Add buildNumberVersionSuffix to BuildNumberMojo