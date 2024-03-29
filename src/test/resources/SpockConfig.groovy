/*
 * MIT License
 *
 * Copyright (c) 2022 - present Gregor Anders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
//file:noinspection GrUnresolvedAccess
report {
    issueNamePrefix ''
    issueUrlPrefix 'https://github.com/gregoranders/gradle-dependencies-check/issues/'
}

spockReports {
    set 'com.athaydes.spockframework.report.IReportCreator': 'com.athaydes.spockframework.report.template.TemplateReportCreator'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.specTemplateFile': '/spockreporttemplates/spec-template.md'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.reportFileExtension': 'md'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryTemplateFile': '/spockreporttemplates/summary-template.md'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.summaryFileName': 'index.md'
    set 'com.athaydes.spockframework.report.template.TemplateReportCreator.enabled': true

    set 'com.athaydes.spockframework.report.aggregatedJsonReportDir': 'build/results/spock'
    set 'com.athaydes.spockframework.report.showCodeBlocks': true
    set 'com.athaydes.spockframework.report.outputDir': 'build/reports/spock'
}

unroll {
    includeFeatureNameForIterations false
}

runner {
    filterStackTrace false
    optimizeRunOrder true
    parallel {
        enabled true
    }
}
