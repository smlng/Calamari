package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.CommitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.NuGetPublishStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.nuGetPublish
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'PublishToFeedzIo'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("PublishToFeedzIo")) {
    check(name == "Publish to Feedz.io") {
        "Unexpected name: '$name'"
    }
    name = "Chain: Build and Test and Publish to Feedz.io"

    vcs {
        remove(DslContext.settingsRoot.id!!)
        add(AbsoluteId("OctopusDeploy_LIbraries_Sashimi_SharedGitHubVcsRoot"))
    }

    expectSteps {
        nuGetPublish {
            name = "Nuget Publish"
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            packages = "*.nupkg"
            serverUrl = "%InternalNuget.OctopusDependeciesFeedUrl%"
            apiKey = "%nuGetPublish.apiKey%"
            args = "-Timeout 1200"
        }
    }
    steps {
        update<NuGetPublishStep>(0) {
            clearConditions()
            apiKey = "credentialsJSON:a7d4426a-7256-4df7-a953-266292e6ad81"
            param("org.jfrog.artifactory.selectedDeployableServer.downloadSpecSource", "Job configuration")
            param("org.jfrog.artifactory.selectedDeployableServer.useSpecs", "false")
            param("org.jfrog.artifactory.selectedDeployableServer.uploadSpecSource", "Job configuration")
        }
    }

    triggers {
        val trigger1 = find<VcsTrigger> {
            vcs {
                branchFilter = """
                    ## We actually want to publish all builds
                    +:refs/tags/*
                    +:<default>
                    +:refs/heads/*
                """.trimIndent()
            }
        }
        trigger1.apply {
            branchFilter = """
                +:<default>
                +:pull/*
                +:refs/tags/*
            """.trimIndent()

        }
    }

    features {
        val feature1 = find<CommitStatusPublisher> {
            commitStatusPublisher {
                publisher = github {
                    githubUrl = "https://api.github.com"
                    authType = personalToken {
                        token = "%commitStatusPublisher.apiKey%"
                    }
                }
            }
        }
        feature1.apply {
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:d2d6ff31-56f1-4893-a448-f7a517da6c88"
                }
            }
        }
        add {
            pullRequests {
                provider = github {
                    authType = token {
                        token = "credentialsJSON:e3abf97f-cad5-4d88-9a7a-f588c55c53ed"
                    }
                    filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
                }
            }
        }
    }

    dependencies {
        expect(RelativeId("NetcoreTesting_AmazonLinux")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_AmazonLinux")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_Ubuntu")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_Ubuntu")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_OpenSUSE")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_OpenSUSE")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        remove(RelativeId("NetcoreTesting_Sles")) {
            snapshot {
            }
        }

        expect(RelativeId("NetcoreTesting_CentOS")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_CentOS")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_Fedora")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_Fedora")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_Debian")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_Debian")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_Rhel")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_Rhel")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("NetcoreTesting_Windows")) {
            snapshot {
            }
        }
        update(RelativeId("NetcoreTesting_Windows")) {
            snapshot {
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        remove(RelativeId("NetcoreTesting_MacOsx")) {
            snapshot {
            }
        }

        remove(RelativeId("WindowsNetFxTesting_2008")) {
            snapshot {
            }
        }

        remove(RelativeId("WindowsNetFxTesting_2008r2")) {
            snapshot {
            }
        }

        expect(RelativeId("WindowsNetFxTesting_2012")) {
            snapshot {
            }
        }
        update(RelativeId("WindowsNetFxTesting_2012")) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("WindowsNetFxTesting_2012r2")) {
            snapshot {
            }
        }
        update(RelativeId("WindowsNetFxTesting_2012r2")) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("WindowsNetFxTesting_2016")) {
            snapshot {
            }
        }
        update(RelativeId("WindowsNetFxTesting_2016")) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

        expect(RelativeId("WindowsNetFxTesting_2019")) {
            snapshot {
            }
        }
        update(RelativeId("WindowsNetFxTesting_2019")) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.CANCEL
                onDependencyCancel = FailureAction.CANCEL
            }
        }

    }

    requirements {
        remove {
            equals("system.Octopus.Purpose", "Build")
        }
        add {
            startsWith("system.agent.name", "nautilus-")
        }
    }
}
