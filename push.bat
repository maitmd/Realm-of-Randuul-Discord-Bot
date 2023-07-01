git add *
set /p "msg=Enter Commit Message: "
git commit -a -m "%msg%"
git push
