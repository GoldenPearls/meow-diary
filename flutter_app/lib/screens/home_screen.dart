import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/auth_provider.dart';
import '../providers/cat_provider.dart';
import 'cats/cats_screen.dart';
import 'health/health_records_screen.dart';
import 'community/community_screen.dart';
import 'profile/profile_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _currentIndex = 0;
  
  final List<Widget> _screens = [
    const HomeTabScreen(),
    const CatsScreen(),
    const HealthRecordsScreen(),
    const CommunityScreen(),
    const ProfileScreen(),
  ];

  @override
  void initState() {
    super.initState();
    // 고양이 목록 로드
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<CatProvider>(context, listen: false).loadCats();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: IndexedStack(
        index: _currentIndex,
        children: _screens,
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: '홈',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.pets),
            label: '내 고양이',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.favorite),
            label: '건강기록',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.people),
            label: '커뮤니티',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: '프로필',
          ),
        ],
      ),
    );
  }
}

class HomeTabScreen extends StatelessWidget {
  const HomeTabScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('MeowDiary'),
        actions: [
          Consumer<AuthProvider>(
            builder: (context, authProvider, child) {
              return TextButton.icon(
                onPressed: () async {
                  await authProvider.logout();
                  if (context.mounted) {
                    Navigator.of(context).pushReplacementNamed('/login');
                  }
                },
                icon: const Icon(Icons.logout, color: Colors.white),
                label: const Text(
                  '로그아웃',
                  style: TextStyle(color: Colors.white),
                ),
              );
            },
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // 환영 메시지
            Consumer<AuthProvider>(
              builder: (context, authProvider, child) {
                return Card(
                  child: Padding(
                    padding: const EdgeInsets.all(20),
                    child: Row(
                      children: [
                        const Icon(
                          Icons.pets,
                          size: 40,
                          color: Color(0xFF6C5CE7),
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                '안녕하세요, ${authProvider.currentUser?.nickname ?? '사용자'}님!',
                                style: Theme.of(context).textTheme.headlineSmall,
                              ),
                              const SizedBox(height: 4),
                              const Text(
                                '오늘도 고양이와 함께 행복한 하루 보내세요 🐾',
                                style: TextStyle(color: Colors.grey),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
            const SizedBox(height: 24),
            // 주요 기능
            Text(
              '주요 기능',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            GridView.count(
              crossAxisCount: 2,
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              crossAxisSpacing: 16,
              mainAxisSpacing: 16,
              children: [
                _FeatureCard(
                  icon: Icons.pets,
                  title: '고양이 관리',
                  subtitle: '내 고양이들의 프로필을 관리하세요',
                  color: const Color(0xFF6C5CE7),
                  onTap: () {
                    // 고양이 탭으로 이동
                  },
                ),
                _FeatureCard(
                  icon: Icons.favorite,
                  title: '건강 기록',
                  subtitle: '체중, 체온 등 건강 정보를 기록하세요',
                  color: const Color(0xFFE17055),
                  onTap: () {
                    // 건강 기록 탭으로 이동
                  },
                ),
                _FeatureCard(
                  icon: Icons.people,
                  title: '커뮤니티',
                  subtitle: '다른 집사들과 정보를 공유하세요',
                  color: const Color(0xFF74B9FF),
                  onTap: () {
                    // 커뮤니티 탭으로 이동
                  },
                ),
                _FeatureCard(
                  icon: Icons.smart_toy,
                  title: 'AI 조언',
                  subtitle: 'AI가 고양이 건강을 분석해드려요',
                  color: const Color(0xFFA29BFE),
                  onTap: () {
                    // AI 조언 페이지로 이동
                  },
                ),
              ],
            ),
            const SizedBox(height: 24),
            // 최근 활동
            Text(
              '최근 활동',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            Consumer<CatProvider>(
              builder: (context, catProvider, child) {
                if (catProvider.isLoading) {
                  return const Center(child: CircularProgressIndicator());
                }
                
                if (catProvider.cats.isEmpty) {
                  return Card(
                    child: Padding(
                      padding: const EdgeInsets.all(20),
                      child: Column(
                        children: [
                          const Icon(
                            Icons.pets,
                            size: 48,
                            color: Colors.grey,
                          ),
                          const SizedBox(height: 16),
                          const Text(
                            '아직 등록된 고양이가 없어요',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 8),
                          const Text(
                            '첫 번째 고양이를 등록해보세요!',
                            style: TextStyle(color: Colors.grey),
                          ),
                          const SizedBox(height: 16),
                          ElevatedButton.icon(
                            onPressed: () {
                              // 고양이 등록 페이지로 이동
                            },
                            icon: const Icon(Icons.add),
                            label: const Text('고양이 등록하기'),
                          ),
                        ],
                      ),
                    ),
                  );
                }
                
                return ListView.builder(
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  itemCount: catProvider.cats.length > 3 ? 3 : catProvider.cats.length,
                  itemBuilder: (context, index) {
                    final cat = catProvider.cats[index];
                    return Card(
                      child: ListTile(
                        leading: CircleAvatar(
                          backgroundImage: cat.profileImageUrl != null
                              ? NetworkImage(cat.profileImageUrl!)
                              : null,
                          child: cat.profileImageUrl == null
                              ? const Icon(Icons.pets)
                              : null,
                        ),
                        title: Text(cat.name),
                        subtitle: Text(cat.breed ?? '품종 미상'),
                        trailing: Text('${cat.age}세'),
                        onTap: () {
                          catProvider.selectCat(cat);
                          // 고양이 상세 페이지로 이동
                        },
                      ),
                    );
                  },
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}

class _FeatureCard extends StatelessWidget {
  final IconData icon;
  final String title;
  final String subtitle;
  final Color color;
  final VoidCallback onTap;

  const _FeatureCard({
    required this.icon,
    required this.title,
    required this.subtitle,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(16),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                width: 48,
                height: 48,
                decoration: BoxDecoration(
                  color: color.withOpacity(0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Icon(
                  icon,
                  color: color,
                  size: 24,
                ),
              ),
              const SizedBox(height: 12),
              Text(
                title,
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 4),
              Text(
                subtitle,
                style: const TextStyle(
                  fontSize: 12,
                  color: Colors.grey,
                ),
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
              ),
            ],
          ),
        ),
      ),
    );
  }
}