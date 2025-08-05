import 'package:flutter/material.dart';
import '../models/user.dart';
import '../services/auth_service.dart';

class AuthProvider with ChangeNotifier {
  final AuthService _authService = AuthService();
  
  User? _currentUser;
  bool _isLoading = false;
  bool _isLoggedIn = false;
  
  User? get currentUser => _currentUser;
  bool get isLoading => _isLoading;
  bool get isLoggedIn => _isLoggedIn;
  
  AuthProvider() {
    _checkLoginStatus();
  }
  
  Future<void> _checkLoginStatus() async {
    _isLoading = true;
    notifyListeners();
    
    try {
      _isLoggedIn = await _authService.isLoggedIn();
      if (_isLoggedIn) {
        _currentUser = await _authService.getCurrentUser();
      }
    } catch (e) {
      _isLoggedIn = false;
      _currentUser = null;
    }
    
    _isLoading = false;
    notifyListeners();
  }
  
  Future<bool> login(String username, String password) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      final loginResponse = await _authService.login(username, password);
      _currentUser = User(
        id: loginResponse.userId,
        username: loginResponse.username,
        nickname: loginResponse.nickname,
        firstName: loginResponse.firstName,
        lastName: loginResponse.lastName,
        email: loginResponse.email,
        phone: null,
        address: null,
        isActive: true,
        emailVerified: loginResponse.emailVerified,
        createdAt: DateTime.now(),
        updatedAt: DateTime.now(),
      );
      _isLoggedIn = true;
      
      _isLoading = false;
      notifyListeners();
      return true;
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      throw Exception(e.toString());
    }
  }
  
  Future<bool> register(UserRegistrationRequest request) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      await _authService.register(request);
      _isLoading = false;
      notifyListeners();
      return true;
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      throw Exception(e.toString());
    }
  }
  
  Future<void> logout() async {
    _isLoading = true;
    notifyListeners();
    
    try {
      await _authService.logout();
    } catch (e) {
      // 로그아웃 실패해도 로컬 상태는 초기화
    }
    
    _currentUser = null;
    _isLoggedIn = false;
    _isLoading = false;
    notifyListeners();
  }
  
  Future<bool> checkDuplicate(String value, String type) async {
    try {
      return await _authService.checkDuplicate(value, type);
    } catch (e) {
      throw Exception(e.toString());
    }
  }
}