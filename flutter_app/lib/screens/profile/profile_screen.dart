import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/auth_provider.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('프로필'),
      ),
      body: Consumer<AuthProvider>(
        builder: (context, authProvider, child) {
          final user = authProvider.currentUser;
          
          if (user == null) {
            return const Center(
              child: Text('사용자 정보를 불러올 수 없습니다'),
            );
          }

          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),
            child: Column(
              children: [
                // 프로필 카드
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(20),
                    child: Column(
                      children: [
                        CircleAvatar(
                          radius: 50,
                          backgroundColor: Theme.of(context).primaryColor,
                          child: Text(
                            user.nickname.isNotEmpty 
                                ? user.nickname[0].toUpperCase()
                                : 'U',
                            style: const TextStyle(
                              fontSize: 32,
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                        ),
                        const SizedBox(height: 16),
                        Text(
                          user.nickname,
                          style: const TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Text(
                          user.email,
                          style: const TextStyle(
                            color: Colors.grey,
                            fontSize: 16,
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                // 정보 카드
                Card(
                  child: Column(
                    children: [
                      ListTile(
                        leading: const Icon(Icons.person),
                        title: const Text('이름'),
                        subtitle: Text('${user.lastName} ${user.firstName}'),
                      ),
                      const Divider(),
                      ListTile(
                        leading: const Icon(Icons.alternate_email),
                        title: const Text('아이디'),
                        subtitle: Text(user.username),
                      ),
                      const Divider(),
                      ListTile(
                        leading: const Icon(Icons.email),
                        title: const Text('이메일'),
                        subtitle: Text(user.email),
                        trailing: user.emailVerified
                            ? const Icon(Icons.verified, color: Colors.green)
                            : const Icon(Icons.error, color: Colors.orange),
                      ),
                      if (user.phone != null) ...[
                        const Divider(),
                        ListTile(
                          leading: const Icon(Icons.phone),
                          title: const Text('전화번호'),
                          subtitle: Text(user.phone!),
                        ),
                      ],
                      if (user.address != null) ...[
                        const Divider(),
                        ListTile(
                          leading: const Icon(Icons.location_on),
                          title: const Text('주소'),
                          subtitle: Text(user.address!),
                        ),
                      ],
                    ],
                  ),
                ),
                const SizedBox(height: 16),
                // 설정 카드
                Card(
                  child: Column(
                    children: [
                      ListTile(
                        leading: const Icon(Icons.edit),
                        title: const Text('프로필 수정'),
                        trailing: const Icon(Icons.chevron_right),
                        onTap: () {
                          // 프로필 수정 페이지로 이동
                        },
                      ),
                      const Divider(),
                      ListTile(
                        leading: const Icon(Icons.settings),
                        title: const Text('설정'),
                        trailing: const Icon(Icons.chevron_right),
                        onTap: () {
                          // 설정 페이지로 이동
                        },
                      ),
                      const Divider(),
                      ListTile(
                        leading: const Icon(Icons.help),
                        title: const Text('도움말'),
                        trailing: const Icon(Icons.chevron_right),
                        onTap: () {
                          // 도움말 페이지로 이동
                        },
                      ),
                      const Divider(),
                      ListTile(
                        leading: const Icon(Icons.logout, color: Colors.red),
                        title: const Text('로그아웃'),
                        onTap: () async {
                          final confirmed = await showDialog<bool>(
                            context: context,
                            builder: (context) => AlertDialog(
                              title: const Text('로그아웃'),
                              content: const Text('정말 로그아웃하시겠습니까?'),
                              actions: [
                                TextButton(
                                  onPressed: () => Navigator.of(context).pop(false),
                                  child: const Text('취소'),
                                ),
                                TextButton(
                                  onPressed: () => Navigator.of(context).pop(true),
                                  child: const Text('로그아웃'),
                                ),
                              ],
                            ),
                          );

                          if (confirmed == true) {
                            await authProvider.logout();
                            if (context.mounted) {
                              Navigator.of(context).pushReplacementNamed('/login');
                            }
                          }
                        },
                      ),
                    ],
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}