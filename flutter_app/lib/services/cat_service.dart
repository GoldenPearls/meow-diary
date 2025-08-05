import 'dart:io';
import 'package:dio/dio.dart';
import '../models/cat.dart';
import 'api_service.dart';

class CatService {
  final ApiService _apiService = ApiService();
  
  Future<List<Cat>> getCatsByUserId(int userId) async {
    try {
      final response = await _apiService.get('/cats/user/$userId');
      final List<dynamic> catsJson = response.data;
      return catsJson.map((json) => Cat.fromJson(json)).toList();
    } catch (e) {
      throw Exception('고양이 목록을 가져오는데 실패했습니다: $e');
    }
  }
  
  Future<Cat> getCatById(int id) async {
    try {
      final response = await _apiService.get('/cats/$id');
      return Cat.fromJson(response.data);
    } catch (e) {
      throw Exception('고양이 정보를 가져오는데 실패했습니다: $e');
    }
  }
  
  Future<Cat> createCat(int userId, CatCreateRequest request, {File? imageFile}) async {
    try {
      if (imageFile != null) {
        // 이미지와 함께 고양이 등록
        final formData = FormData.fromMap({
          'userId': userId.toString(),
          'name': request.name,
          'breed': request.breed ?? '',
          'color': request.color ?? '',
          'birthDate': request.birthDate?.toIso8601String() ?? '',
          'weight': request.weight?.toString() ?? '',
          'gender': request.gender?.name ?? '',
          'isNeutered': request.isNeutered.toString(),
          'description': request.description ?? '',
          'image': await MultipartFile.fromFile(
            imageFile.path,
            filename: 'cat_image.jpg',
          ),
        });
        
        final response = await _apiService.postFormData('/cats', formData);
        return Cat.fromJson(response.data);
      } else {
        // JSON으로 고양이 등록
        final response = await _apiService.post('/cats/json?userId=$userId', 
            data: request.toJson());
        return Cat.fromJson(response.data);
      }
    } catch (e) {
      throw Exception('고양이 등록에 실패했습니다: $e');
    }
  }
  
  Future<Cat> updateCat(int id, CatUpdateRequest request, {File? imageFile}) async {
    try {
      if (imageFile != null) {
        // 이미지와 함께 고양이 수정
        final formData = FormData.fromMap({
          'name': request.name,
          'breed': request.breed,
          'color': request.color,
          'birthDate': request.birthDate?.toIso8601String(),
          'weight': request.weight?.toString(),
          'gender': request.gender?.name,
          'isNeutered': request.isNeutered?.toString(),
          'description': request.description,
          'image': await MultipartFile.fromFile(
            imageFile.path,
            filename: 'cat_image.jpg',
          ),
        });
        
        final response = await _apiService.postFormData('/cats/$id', formData);
        return Cat.fromJson(response.data);
      } else {
        // JSON으로 고양이 수정
        final response = await _apiService.put('/cats/$id', data: request.toJson());
        return Cat.fromJson(response.data);
      }
    } catch (e) {
      throw Exception('고양이 정보 수정에 실패했습니다: $e');
    }
  }
  
  Future<void> deleteCat(int id) async {
    try {
      await _apiService.delete('/cats/$id');
    } catch (e) {
      throw Exception('고양이 삭제에 실패했습니다: $e');
    }
  }
}