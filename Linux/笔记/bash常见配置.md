set -o notify
alias jobs="jobs -l"
alias rm="rm -i"
alias cp="cp -i"

export VIRTUALENVWRAPPER_PYTHON=/usr/bin/python3
export WORKON_HOME=$HOME/.virtualenvs
export PROJECT_HOME=$HOME/Devel
source /usr/local/bin/virtualenvwrapper.sh

alias sls="screen -ls"
alias sr="screen -r"