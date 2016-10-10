#!/usr/bin/env bash
# spring boot service have a better solution
# see http://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html#deployment-service
#
# manage service
# support simple start, sotp, restart actions
# author: lvhao

# require jdk1.8+
EXPORT JAVA_HOME=xxxx
EXPORT CLASSPATH=yyyy

#log path
EXPORT LOG_PATH=/zzz

# spring boot variable
SPRING_CONFIG_NAME=app,datasource,quartz,redis

log()
{
	log -e "\n$1\n"
}

check_args()
{
    local params_len = $#
    if [ 2 -ne params_len ]; then
        log "script must be passed two arguments."
        log "sh service.sh app_name action"
        exit
    else
        init_global_variable $1 $2
    fi
}

detect_service_status()
{
    `ps -ef | grep $1 | grep -v grep | awk '{print $2}'`
}

service_status()
{
    detect_service_status $1
    if [ 0 -lt $? ]; then
        log "$1 is running"
    else
        log "$1 is not running"
    fi
}

start_service()
{
    local service_name = $1
    log "${service_name} start..."
    detect_service_status ${service_name}
    if [ -1 -ne SERVICE_PID ]; then
        stop_service ${service_name}
    fi
    nohup java -jar schedule-job-1.0.0.jar --port=8888  > /dev/null > 2>&1 &
    log "${service_name} start done..."
}

stop_service()
{
    local service_name = $1
    log "${service_name} stop..."
    detect_service_status ${service_name}
    kill -9 $?
    log "${service_name} stop done..."
}

restart()
{
    local service_name = $1
    start_service ${service_name}
    if [ 0 -ne $? ]; then
        stop_service ${service_name}
    fi
}

service_action()
{
    local service_name = $1
    local action = $2
    #deal with the operation
    case "${action}" in
        start)
        start_service ${service_name}
        exit $?
        ;;

      status|show)

        ;;

      stop)
        stop_service ${service_name}
        exit $?
        ;;

      restart)
        restart ${service_name}
        exit $?
        ;;

        *)
        log "Usage: service.sh service_name start/stop/restart/show/status/"
        exit 1
        ;;
    esac
}

main()
{
    check_args $@
    service_action $@
}
main $@