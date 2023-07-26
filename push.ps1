git add *
$MSG=Read-Host "Enter a commit message"
git commit -a -m $MSG
git push
curl http://192.168.254.156:8081/job/Deploy%20Genso/build?token=11ba6d22f6ab9b626f186094f6afb9f243 -UseBasicParsing