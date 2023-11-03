git add .
$MSG=Read-Host "Enter a commit message"
git commit -m $MSG
git push
curl http://192.168.254.156:8083/job/Update%20and%20Push%20Genso/build?token=11b3696dae5447da70b4bdc3a172ced684 -UseBasicParsing