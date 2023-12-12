class PipelineUtility implements Serializable
{


  def static markBuildStatus(script,pipelineStage,err,pipelinGitlabState,currentBuildResult)
  {
      script.echo "Build failed at ${pipelineStage} stage with Error ${err}"
      if("${err}" == "org.jenkinsci.plugins.workflow.steps.FlowInterruptedException")
      {
          script.updateGitlabCommitStatus name: "${pipelineStage}", state: 'canceled'
          script.currentBuild.result = 'ABORTED'
      }
      else
      {
          script.updateGitlabCommitStatus name: "${pipelineStage}", state: "${pipelinGitlabState.toLowerCase()}"
          script.currentBuild.result = "${currentBuildResult.toUpperCase()}"
          script.error "Build failed at ${pipelineStage} stage with Error ${err}"
          script.sh 'exit 1'
      }
  }
}
