echo publishing %1

nuget push %1 -Source https://www.nuget.org

pause