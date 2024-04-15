Set objShell = CreateObject("WScript.Shell")

' Add changes to stage
objShell.Run "cmd /k git add .", 1, True

' Prompt user for commit message
commit_message = InputBox("Enter the commit message:", "Commit Message")

' Commit changes with the provided message
commitCommand = "cmd /c git commit -m """ & commit_message & """"
errorCode = objShell.Run(commitCommand, 0, True)

' Check if commit command was successful
If errorCode <> 0 Then
    MsgBox "Error: Failed to commit changes!", vbCritical, "Error"
End If

' Push changes to remote front-end branch
pushCommand = "cmd /c git push origin front-end"
errorCode = objShell.Run(pushCommand, 0, True)

' Check if push command was successful
If errorCode <> 0 Then
    MsgBox "Error: Failed to push changes!", vbCritical, "Error"
End If

' Release resources
Set objShell = Nothing
