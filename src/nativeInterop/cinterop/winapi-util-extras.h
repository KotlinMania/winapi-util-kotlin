#ifndef WINAPI_UTIL_EXTRAS_H
#define WINAPI_UTIL_EXTRAS_H

/* Include windows.h first so that _mingw.h defines EXTERN_C, the base
   Win32 types (HANDLE, DWORD, BOOL, COORD, etc.), and the Win32
   calling-convention macros before any of the declarations below. */
#include <windows.h>
#include <consoleapi.h>
#include <fileapi.h>
#include <sysinfoapi.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Console functions from kernel32.dll. */
BOOL __stdcall GetConsoleMode(HANDLE hConsoleHandle, LPDWORD lpMode);
BOOL __stdcall SetConsoleMode(HANDLE hConsoleHandle, DWORD dwMode);
BOOL __stdcall GetConsoleScreenBufferInfo(HANDLE hConsoleOutput, PCONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
BOOL __stdcall SetConsoleTextAttribute(HANDLE hConsoleOutput, WORD wAttributes);

/* File functions from kernel32.dll. */
BOOL __stdcall GetFileInformationByHandle(HANDLE hFile, LPBY_HANDLE_FILE_INFORMATION lpFileInformation);
DWORD __stdcall GetFileType(HANDLE hFile);
HANDLE __stdcall CreateFileW(LPCWSTR lpFileName, DWORD dwDesiredAccess, DWORD dwShareMode, LPSECURITY_ATTRIBUTES lpSecurityAttributes, DWORD dwCreationDisposition, DWORD dwFlagsAndAttributes, HANDLE hTemplateFile);
HANDLE __stdcall GetStdHandle(DWORD nStdHandle);

/* System information functions from kernel32.dll. */
BOOL __stdcall GetComputerNameExW(
    COMPUTER_NAME_FORMAT NameType,
    LPWSTR lpBuffer,
    LPDWORD nSize);

#ifdef __cplusplus
}
#endif

#endif /* WINAPI_UTIL_EXTRAS_H */