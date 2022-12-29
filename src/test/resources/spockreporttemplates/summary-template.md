---
layout: default
title: ${projectName}
---
<% if (projectName && projectVersion) { %>
# ${projectName}:${projectVersion}
<% } %>

### Created {{ "${ new Date() }" | timeago }}

## Specifications summary
<% def stats = utils.aggregateStats( data ) %>

| Total          | Passed          | Failed          | Feature failures | Feature errors   | Success rate         | Total time (ms) |
|----------------|-----------------|-----------------|------------------|------------------|----------------------|-----------------|
| ${stats.total} | ${stats.passed} | ${stats.failed} | ${stats.fFails}  | ${stats.fErrors} | ${stats.successRate} | ${stats.time}   |

## Specifications

| Name | Features | Failed | Errors | Skipped | Success rate | Time |
|------|----------|--------|--------|---------|--------------|------|
<% data.each { name, map ->
  def fStats = map.stats
  def linkedName = '[' + name + '](' + name + ')'
%>| ${linkedName} | ${fStats.totalFeatures} | ${fStats.failures} | ${fStats.errors} | ${fStats.skipped} | ${fStats.successRate} | ${fStats.time} |
<% } %>
