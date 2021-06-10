#!/usr/bin/env bash

dateTime=${1} #20190117
input=./data/${dateTime}
outputQueryEntity=./data/queryEntity/${dateTime}
outputTemplateCount=./data/templateCount/${dateTime}
outputDomainCount=./data/domainCount/${dateTime}

hdfs --cluster zjyprc-hadoop dfs -rm -r -skipTrash ${outputQueryEntity} ${outputTemplateCount} ${outputDomainCount}

spark-submit  --java 8 --cluster zjyprc-hadoop \
    --conf spark.yarn.access.namenodes=hdfs://zjyprc-hadoop \
    --conf spark.yarn.job.owners=caoxiaoqiang \
    --class com.xiaomi.qabot.services.ParseQabotLog \
    --master yarn-cluster \
    --queue root.production.cloud_group.QABot.qabot \
    --driver-memory 2g \
    --executor-cores 2 \
    --executor-memory 5g  \
    --conf spark.executorEnv.JAVA_HOME=/opt/soft/jdk1.8.0 \
    --conf spark.yarn.appMasterEnv.JAVA_HOME=/opt/soft/jdk1.8.0 \
    --conf "spark.executor.extraJavaOptions=-XX:MaxDirectMemorySize=512m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./spark-executor-heapdump.hprof -XX:OnOutOfMemoryError='./oom_script.sh %p'" \
    --conf spark.driver.maxResultSize=6g \
    --conf spark.dynamicAllocation.enabled=true \
    --conf spark.dynamicAllocation.initialExecutors=1 \
    --conf spark.dynamicAllocation.maxExecutors=100  \
    --conf spark.default.parallelism=100  \
    ./template_mining-1.0-SNAPSHOT.jar  \
    --repartition 5000 \
    --combine_partition 100 \
    --input  ${input} \
    --outputTemplateCount  ${outputTemplateCount} \
    --outputDomainCount  ${outputDomainCount} \
    --outputQueryEntity ${outputQueryEntity} &> ./logs/${dateTime}


#!/bin/bash

set -e
inputFile=${1}
outputFile=${outputFile}.xlsx
/home/work/java_env/jdk1.8.0_152/bin/java  \
        -Xms1024M -Xmx1024M -Xmn512M -XX:MaxDirectMemorySize=512M \
        -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Xss512K -XX:+UseCompressedOops -XX:+CrashOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError \
        -XX:+PrintReferenceGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintGCApplicationStoppedTime -XX:+PrintPromotionFailure \
        -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=128M -verbose:gc -Xloggc:./logs/gc.log \
        -Dfile.encoding=UTF-8 \
        -cp qa_pair_mining-1.0-SNAPSHOT.jar com.xiaomi.qabot.iaskSina.parse.WriteToExcel \
        inputFile=${inputFile} \
        outputFile=${outputFile} &> logs/log