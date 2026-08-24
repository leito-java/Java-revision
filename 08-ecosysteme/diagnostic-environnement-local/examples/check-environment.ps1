# Ce script effectue uniquement des lectures. Il ne démarre et n'arrête aucun service.
$ErrorActionPreference = 'Continue'

Write-Host '=== Outils Java ==='
java -version
javac -version
mvn -version

Write-Host "`n=== Variables de connexion définies ==="
foreach ($variableName in 'DB_URL', 'DB_USERNAME', 'DB_PASSWORD') {
    $isDefined = [Environment]::GetEnvironmentVariable($variableName) -ne $null
    $displayValue = if ($variableName -eq 'DB_PASSWORD' -and $isDefined) { '<masqué>' } else { $isDefined }
    Write-Host "${variableName}: $displayValue"
}

Write-Host "`n=== Ports en écoute ==="
$ports = 4200, 8080, 5432, 5433
$listeners = Get-NetTCPConnection -State Listen -ErrorAction SilentlyContinue |
    Where-Object LocalPort -In $ports |
    Select-Object LocalAddress, LocalPort, OwningProcess

if ($listeners) {
    $listeners | Format-Table -AutoSize
} else {
    Write-Host 'Aucun des ports attendus ne répond.'
}

Write-Host "`n=== API ==="
try {
    $tasks = Invoke-RestMethod -Uri 'http://localhost:8080/api/tasks' -TimeoutSec 3
    Write-Host "API accessible. Nombre de tâches: $(@($tasks).Count)"
} catch {
    Write-Host "API inaccessible: $($_.Exception.Message)"
}
