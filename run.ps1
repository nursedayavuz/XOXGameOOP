$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
if (-not (Test-Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}
javac -encoding UTF-8 -d out src\*.java
java -cp out XOXGame
