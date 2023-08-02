#include "UsernameProvider.h"
#include "spdlog/spdlog.h"

#ifdef _WIN32

#undef UNICODE
#undef _UNICODE
#include <windows.h>
#include <Lmcons.h>
#include <DSRole.h>
#include <locale>
#include <codecvt>

#pragma comment(lib, "netapi32.lib")

#endif

static std::string GetWindowsUsername();

std::string UsernameProvider::GetUsername()
{
#ifdef _WIN32
	return GetWindowsUsername();
#endif
}

static std::string ConvertToAscii(const std::wstring &a_str)
{
	using convert_type = std::codecvt_utf8<wchar_t>;
	std::wstring_convert<convert_type, wchar_t> converter;

	//use converter (.to_bytes: wstr->str, .from_bytes: str->wstr)
	return converter.to_bytes( a_str );
}

#ifdef _WIN32

static std::string GetDomainName()
{
	// from https://stackoverflow.com/questions/9792411/how-to-get-windows-domain-name
	DSROLE_PRIMARY_DOMAIN_INFO_BASIC * info;
    DWORD dw;


    dw = DsRoleGetPrimaryDomainInformation(NULL,
                                           DsRolePrimaryDomainInfoBasic,
                                           (PBYTE *)&info);
    if (dw != ERROR_SUCCESS)
    {
		spdlog::error("DsRoleGetPrimaryDomainInformation error: {}", dw);
        return "";
    }

	std::wstring result;
    if (info->DomainNameFlat != NULL)
    {
		result = info->DomainNameFlat;
    }
	else if (info->DomainNameDns != NULL)
	{
		result = info->DomainNameDns;
	}

	DsRoleFreeMemory(info);

	return ConvertToAscii(result);
}

static std::string GetWindowsUsername()
{
	// This is the windows way, for ASCII
	// If you haven't yet - then You should start using unicode!
	char winUsername[UNLEN+1];
	DWORD usernameLen = UNLEN+1;
	GetUserNameA(winUsername, &usernameLen);
	return std::string(winUsername, winUsername + usernameLen);

	// For LINUX try this (untested):
	// NOTE: this is NOT a good, or safe, or reliable way!
	// The environment variable is easily faked.
	// LINUX has special methods for this but they are beyond the scope of this workshop
	//
	//const char * username = getenv("USER");
	//return username;
}

#endif
