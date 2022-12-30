const conventionalCommit = require('./conventionalcommit.json');

const typesEnum = Object.keys(conventionalCommit.types);
const scopesEnum = Object.keys(conventionalCommit.commonScopes);

module.exports = {
  extends: ['@commitlint/config-conventional'],
  parserPreset: {
    parserOpts: {
      mergePattern: /^Merge branch '(.*)' into '(.*)$/,
      mergeCorrespondence: ['source', 'target'],
      issuePrefixes: ['#'],
      noteKeywords: ["BREAKING-CHANGE"]
    }
  },
  formatter: '@commitlint/format',
  rules: {
    'type-enum': [2, 'always', typesEnum],
    'type-empty': [2, 'never'],
    'type-case': [2, 'always', ['camel-case']],
    'scope-enum': [1, 'always', scopesEnum],
    'scope-empty': [1, 'always'],
    'scope-case': [2, 'always', ['camel-case']],
    'subject-empty': [2, 'never'],
    'subject-case': [1, 'always', ['lower-case']],
    'header-max-length': [2, 'always', 72],
    'references-empty': [1, 'always']
  },
};
