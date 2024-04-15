Set objShell = CreateObject("WScript.Shell")

' Add changes to stage
objShell.Run "cmd /k git add .", 1, True

' Prompt user for commit message
commit_message = InputBox("Enter the commit message:", "Commit Message")

' Commit changes with the provided message
objShell.Run "cmd /k git commit -m """ & commit_message & """", 1, True

' Push changes to remote front-end branch
objShell.Run "cmd /k git push origin front-end", 1, True

' Release resources
Set objShell = Nothing
