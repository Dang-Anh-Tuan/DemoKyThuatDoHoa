/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoktdh;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

/**
 *
 * @author ADMIN
 */
public class Service {
//================== Draw Line =======================================
//1. Bresemham

    public static List<Point> drawLineByBresemham(int x1, int y1,
            int x2, int y2) {
        List<Point> points = new ArrayList<>();
        int x, y, Dx, Dy, p;
        int x_unit = 1, y_unit = 1;
        Dx = Math.abs(x2 - x1);
        Dy = Math.abs(y2 - y1);
        p = 2 * Dy - Dx;
        x = x1;
        y = y1;
        //xét trường hợp để cho y_unit và x_unit để vẽ tăng lên hay giảm xuống
        if (x2 - x1 < 0) {
            x_unit = -x_unit;
        }
        if (y2 - y1 < 0) {
            y_unit = -y_unit;
        }
        if (x1 == x2) // trường hợp vẽ đường thẳng đứng
        {
            while (y != y2 + 1) {
                y += y_unit;
                points.add(new Point(x, y));
            }
        } else if (y1 == y2) // trường hợp vẽ đường ngang
        {
            while (x != x2 + 1) {
                x += x_unit;
                points.add(new Point(x, y));
            }
        } // trường hợp vẽ các đường xiên
        else {
            points.add(new Point(x, y));
            while (x != x2) {
                if (p < 0) {
                    p += 2 * Dy;
                } else {
                    p += 2 * (Dy - Dx);
                    y += y_unit;
                }
                x += x_unit;
                points.add(new Point(x, y));
            }
        }

        return points;
    }

// 2. Midpoint
    public static List<Point> drawLineByMidpoint(int x1, int y1,
            int x2, int y2) {
        List<Point> points = new ArrayList<>();
        int dx = x2 - x1;
        int dy = y2 - y1;
        if (dy <= dx) {
            int d = dy - (dx / 2);
            int x = x1, y = y1;
          
            points.add(new Point(x, y));

            while (x < x2) {
                x++;

                if (d < 0) {
                    d = d + dy;
                } 
                else {
                    d += (dy - dx);
                    y++;
                }
                points.add(new Point(x, y));
            }
        } else if (dx < dy) {
            int d = dx - (dy / 2);
            int x = x1, y = y1;
            
            points.add(new Point(x, y));
            
            while (y < y2) {
                y++;

                if (d < 0) {
                    d = d + dx;
                }
                else {
                    d += (dx - dy);
                    x++;
                }
             
                points.add(new Point(x, y));
            }
        }
        return points;
    }

//================== Draw Circle =======================================
//1. Bresemham
    public static List<Point> ve4Diem(List<Point> points, int xc, int yc, int x, int y) {
        points.add(new Point(xc + x, yc + y));
        points.add(new Point(xc - x, yc - y));
        points.add(new Point(xc - y, yc + x));
        points.add(new Point(xc + y, yc - x));
        return points;
    }

    public static List<Point> drawCircleByBresemham(int xc, int yc,
            int r) {
        List<Point> points = new ArrayList<>();
        int p = 3 - 2 * r;
        int x, y;
        x = 0;
        y = r;
        points = ve4Diem(points, xc, yc, r, 0);
        while (x < y) {
            if (p < 0) {

                p = p + 4 * x + 6;
            } else {
                p = p + 4 * (x - y) + 10;
                y--;
            }

            x++;
            points = ve4Diem(points, xc, yc, x, y);
            points = ve4Diem(points, xc, yc, y, x);
        }
        // vẽ 4 điểm tại phân giác
        points = ve4Diem(points, xc, yc, y, y);
        return points;
    }
// 2. Midpoint

    public static List<Point> draw8point(List<Point> points, int x, int y, int xc, int yc) {
        points.add(new Point(xc + x, yc + y));
        points.add(new Point(xc - x, yc - y));
        points.add(new Point(xc + x, yc - y));
        points.add(new Point(xc - x, yc + y));
        points.add(new Point(xc + y, yc + x));
        points.add(new Point(xc - y, yc - x));
        points.add(new Point(xc + y, yc - x));
        points.add(new Point(xc - y, yc + x));
        return points;
    }

    public static List<Point> drawCircleByMidpoint(int xc, int yc,
            int r) {
        List<Point> points = new ArrayList<>();
        int x, y, p;
        x = 0;
        y = r;
        p = 5 / 4 - r;
        for (x = 0; x <= y; x++) {
            if (p < 0) {
                p += 2 * x + 3;
            } else {
                p += 2 * (x - y) + 5;
                y--;
            }
            points = draw8point(points, x, y, xc, yc);
        }

        return points;
    }

//================== Draw Ellipse =======================================
    public static List<Point> plot(List<Point> points, int xc, int yc, int x, int y) {
        points.add(new Point(xc + x, yc + y));
        points.add(new Point(xc - x, yc + y));
        points.add(new Point(xc + x, yc - y));
        points.add(new Point(xc - x, yc - y));
        return points;
    }

    public static List<Point> drawEllipse(int xc, int yc,
            int a, int b) {
        int x, y, fx, fy, a2, b2;
        long p;
        List<Point> points = new ArrayList<>();
        x = 0;
        y = b;
        a2 = a * a;
        b2 = b * b;
        fx = 0;
        fy = 2 * a2 * y;
        points = plot(points, xc, yc, x, y);

        p = Math.round(b2 - (a2 * b) + (0.25 * a2));//p=b2 - a2*b +a2/4
        while (fx < fy) {
            x++;
            fx += 2 * b2;
            if (p < 0) {
                p += b2 * (2 * x + 3);//p=p + b2*(2x +3)
            } else {
                y--;
                p += b2 * (2 * x + 3) + a2 * (2 - 2 * y);//p=p +b2(2x +3) +a2(2-2y)
                fy -= 2 * a2;
            }
            points = plot(points, xc, yc, x, y);
        }
        p = Math.round(b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        //
        while (y > 0) {
            y--;
            fy -= 2 * a2;
            if (p >= 0) {
                p += a2 * (3 - 2 * y); //p=p +a2(3-2y)
            } else {
                x++;
                fx += 2 * b2;
                p += b2 * (2 * x + 2) + a2 * (3 - 2 * y);//p=p+ b2(2x +2) + a2(3-2y)
            }
            points = plot(points, xc, yc, x, y);
        }
        return points;
    }
    //================ Util Function ========================================

    public static void paintGeometry(List<Point> points, GraphicsContext brushTool) {
        for (Point point : points) {
            System.out.println(point.getX() + " " + point.getY());
            brushTool.fillRect(point.getX(), point.getY(), 5, 5);
        }
    }

    public static void byebye(GraphicsContext brushTool) {
        Stop[] stops = new Stop[]{new Stop(0, Color.rgb(153, 0, 153)), new Stop(1, Color.rgb(41, 144, 181))};
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0.5, true, CycleMethod.NO_CYCLE, stops);
        brushTool.setFill(linearGradient);
        brushTool.fillRect(0, 0, 1244, 543);
        brushTool.setFont(new Font("VnArial Bold", 56));
        brushTool.setFill(javafx.scene.paint.Color.WHITE);
        brushTool.fillText("Cảm ơn cô và các bạn đã lắng nghe", 200, 200, 800);
        brushTool.fillText("Chúc cả lớp được A+ !!!", 300, 300, 800);

    }

}
