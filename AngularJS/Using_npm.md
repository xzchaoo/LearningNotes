# semver #
https://docs.npmjs.com/misc/semver
## The semantic versioner for npm ##
## Usage ##
npm install semver

## Versions ##
版本前面的v或=会被忽略, v2.0.0,跟=2.0.0,跟2.0.0是一样的意思

## Ranges ##
支持: <, <=, >, >=, =, ||
> For example, the range >=1.2.7 <1.3.0 would match the versions 1.2.7, 1.2.8, and 1.2.99, but not the versions 1.2.6, 1.3.0, or 1.1.0.
> The range 1.2.7 || >=1.2.9 <2.0.0 would match the versions 1.2.7, 1.2.9, and 1.4.6, but not the versions 1.2.8 or 2.0.0.