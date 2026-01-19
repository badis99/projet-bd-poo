$ErrorActionPreference = "Stop"

$libDir = Join-Path $PSScriptRoot "lib"
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir | Out-Null
    Write-Host "Created lib directory at $libDir"
}

# Define dependencies
$dependencies = @(
    @{
        Name = "mysql-connector-j-8.0.33.jar"
        Url = "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"
    },
    @{
        Name = "javafx-base-21.0.1-win.jar"
        Url = "https://repo1.maven.org/maven2/org/openjfx/javafx-base/21.0.1/javafx-base-21.0.1-win.jar"
    },
    @{
        Name = "javafx-controls-21.0.1-win.jar"
        Url = "https://repo1.maven.org/maven2/org/openjfx/javafx-controls/21.0.1/javafx-controls-21.0.1-win.jar"
    },
    @{
        Name = "javafx-graphics-21.0.1-win.jar"
        Url = "https://repo1.maven.org/maven2/org/openjfx/javafx-graphics/21.0.1/javafx-graphics-21.0.1-win.jar"
    },
    @{
        Name = "javafx-fxml-21.0.1-win.jar"
        Url = "https://repo1.maven.org/maven2/org/openjfx/javafx-fxml/21.0.1/javafx-fxml-21.0.1-win.jar"
    }
)

foreach ($dep in $dependencies) {
    $outputPath = Join-Path $libDir $dep.Name
    if (-not (Test-Path $outputPath)) {
        Write-Host "Downloading $($dep.Name)..."
        try {
            Invoke-WebRequest -Uri $dep.Url -OutFile $outputPath
            Write-Host "Downloaded $($dep.Name)"
        } catch {
            Write-Error "Failed to download $($dep.Name): $_"
        }
    } else {
        Write-Host "$($dep.Name) already exists."
    }
}

Write-Host "Dependency setup complete."
