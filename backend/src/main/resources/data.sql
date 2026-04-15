-- 用户数据（密码都是 123456，经过 BCrypt 加密）
INSERT INTO users (username, password, email, phone, address, role, created_at, updated_at)
SELECT 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'admin@fitness.com', '13800138000', '系统管理', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password, email, phone, address, role, created_at, updated_at)
SELECT 'user01', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'user01@example.com', '13800138001', '北京市朝阳区', 0, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user01');

INSERT INTO users (username, password, email, phone, address, role, created_at, updated_at)
SELECT 'user02', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'user02@example.com', '13800138002', '上海市浦东新区', 0, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user02');

-- 商品数据
INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '商用跑步机 Pro', '专业级商用跑步机，配备智能触控屏，支持多种运动模式，适合健身房使用', 15800.00, 50, '跑步机', '/images/treadmill-pro.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '商用跑步机 Pro');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '家用跑步机 Lite', '家用折叠跑步机，静音设计，节省空间，适合家庭健身使用', 3999.00, 100, '跑步机', '/images/treadmill-lite.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '家用跑步机 Lite');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '多功能综合训练器', '集成多种训练功能，可进行胸部、背部、腿部等全身训练', 8500.00, 30, '力量训练', '/images/multi-trainer.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '多功能综合训练器');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '哑铃套装 20kg', '环保材质哑铃，可调节重量，配备收纳架，适合家庭力量训练', 599.00, 200, '力量训练', '/images/dumbbell-20kg.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '哑铃套装 20kg');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '动感单车 S1', '静音磁控动感单车，16档阻力调节，支持APP互联', 2599.00, 80, '健身车', '/images/spin-bike-s1.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '动感单车 S1');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '椭圆机 E500', '静音椭圆机，前后双向运动，保护膝盖，适合全年龄段使用', 4299.00, 60, '健身车', '/images/elliptical-e500.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '椭圆机 E500');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '瑜伽垫 10mm', '加厚防滑瑜伽垫，环保TPE材质，附带绑带', 129.00, 500, '配件', '/images/yoga-mat.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '瑜伽垫 10mm');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '健身手套', '透气防滑健身手套，保护手掌，适合举重和器械训练', 89.00, 300, '配件', '/images/gym-gloves.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '健身手套');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '划船机 R700', '水阻划船机，真实划船体验，锻炼全身80%肌肉群', 5999.00, 40, '有氧设备', '/images/rowing-r700.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '划船机 R700');

INSERT INTO products (name, description, price, stock_quantity, category, image, status, created_at, updated_at)
SELECT '史密斯机', '专业级史密斯机，安全锁定系统，适合大重量训练', 12800.00, 20, '力量训练', '/images/smith-machine.jpg', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '史密斯机');
