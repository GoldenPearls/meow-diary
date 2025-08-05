import 'package:shared_preferences/shared_preferences.dart';
import '../models/user.dart';
import 'api_service.dart';

class AuthService {
  final ApiService _apiService = ApiService();
  
  Future<LoginResponse> login(String username, String password) async {
    try {
      final response = await _apiService.post('/auth/login', data: {
        'username': username,
        'password': password,
      });
      
      final loginResponse = LoginResponse.fromJson(response.data);
      
      // 토큰과 사용자 정보 저장
      await _apiService.saveToken(loginResponse.token);
      await _saveUserInfo(loginResponse);
      
      return loginResponse;
    } catch (e) {
      throw Exception('로그인에 실패했습니다: $e');
    }
  }
  
  Future<User> register(UserRegistrationRequest request) async {
    try {
      final response = await _apiService.post('/users/register', data: request.toJson());
      return User.fromJson(response.data);
    } catch (e) {
      throw Exception('회원가입에 실패했습니다: $e');
    }
  }
  
  Future<void> logout() async {
    try {
      await _apiService.post('/auth/logout');
    } catch (e) {
      // 로그아웃 API 실패해도 로컬 데이터는 삭제
    } finally {
      await _apiService.clearToken();
    }
  }
  
  Future<bool> isLoggedIn() async {
    final token = await _apiService.getToken();
    return token != null;
  }
  
  Future<int?> getCurrentUserId() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getInt('user_id');
  }
  
  Future<String?> getCurrentUsername() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('username');
  }
  
  Future<User> getCurrentUser() async {
    try {
      final response = await _apiService.get('/auth/me');
      return User.fromJson(response.data);
    } catch (e) {
      throw Exception('사용자 정보를 가져오는데 실패했습니다: $e');
    }
  }
  
  Future<bool> checkDuplicate(String value, String type) async {
    try {
      final response = await _apiService.post('/users/check-duplicate', data: {
        'value': value,
        'type': type, // "username", "email", "nickname"
      });
      return response.data['isDuplicate'] ?? false;
    } catch (e) {
      throw Exception('중복 확인에 실패했습니다: $e');
    }
  }
  
  Future<LoginResponse> socialLogin(String provider, String accessToken, {String? idToken}) async {
    try {
      final requestData = {
        'provider': provider,
        'accessToken': accessToken,
      };
      
      if (idToken != null) {
        requestData['idToken'] = idToken;
      }
      
      final response = await _apiService.post('/auth/social-login', data: requestData);
      
      final loginResponse = LoginResponse.fromJson(response.data);
      
      // 토큰과 사용자 정보 저장
      await _apiService.saveToken(loginResponse.token);
      await _saveUserInfo(loginResponse);
      
      return loginResponse;
    } catch (e) {
      throw Exception('소셜 로그인에 실패했습니다: $e');
    }
  }
  
  Future<void> _saveUserInfo(LoginResponse loginResponse) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('user_id', loginResponse.userId);
    await prefs.setString('username', loginResponse.username);
    await prefs.setString('nickname', loginResponse.nickname);
    await prefs.setString('email', loginResponse.email);
  }
}