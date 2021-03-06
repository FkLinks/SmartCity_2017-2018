﻿using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using APIRestSmartCity2017.Model;

namespace APIRestSmartCity2017.Controllers
{
    public abstract class BaseController : Controller
    {
        private UserManager<ApplicationUser> _uMgr;
        public BaseController(UserManager<ApplicationUser> uMgr)
        {
            _uMgr = uMgr;
        }
        protected async Task<ApplicationUser> GetCurrentUserAsync()
        {
            if (this.HttpContext.User == null)
                throw new Exception("L'utilisateur n'est pas identifié");
            Claim userNameClaim = this.HttpContext.User.Claims.FirstOrDefault(claim => claim.Type == ClaimTypes.NameIdentifier);
            if (userNameClaim == null)
                throw new Exception("Le token JWT semble ne pas avoir été interprété correctement");
            return await _uMgr.FindByNameAsync(userNameClaim.Value);
        }

        public bool IsInRole(string roleName)
        {
            Claim roleClaim = this.HttpContext.User.Claims.FirstOrDefault(claim => claim.Type == "Role" && claim.Value == roleName);
            var view = this.HttpContext.User.Claims;
            return roleClaim != null;
        }

        public async Task<IList<String>> GetUsersRoles()
        {
            ApplicationUser applicationUser = await GetCurrentUserAsync();
            return await _uMgr.GetRolesAsync(applicationUser);
        }
    }
}
