git add *
set /p "msg=Enter Commit Message: "
git commit -a -m "%msg%"
git push
curl "http://192.168.254.156:8081/job/Deploy%20Genso/build?token=xokorjpbqfzjfhfglhtwimcpxgdqrv"