git add *
set /p "msg=Enter Commit Message: "
git commit -a -m "%msg%"
git push
curl http://192.168.254.156:8081/job/Deploy%20Genso/build?token=11ba6d22f6ab9b626f186094f6afb9f243 -UseBasicParsing