package com.sunjee.btms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sunjee.util.HqlNoEquals;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.Area;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.ShelfDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.AreaService;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.ShelfService;

@Service("shelfService")
public class ShelfServiceImpl implements ShelfService {

    private ShelfDao shelfDao;

    private AreaService areaService;
    private BlessSeatService blessSeatService;

    public ShelfDao getShelfDao() {
        return shelfDao;
    }

    @Resource(name = "shelfDao")
    public void setShelfDao(ShelfDao shelfDao) {
        this.shelfDao = shelfDao;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    @Resource(name = "areaService")
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    public BlessSeatService getBlessSeatService() {
        return blessSeatService;
    }

    @Resource(name = "blessSeatService")
    public void setBlessSeatService(BlessSeatService blessSeatService) {
        this.blessSeatService = blessSeatService;
    }

    @Override
    public DataGrid<Shelf> getDataGrid(Pager page,
                                       Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.shelfDao.getDataGrid(page, whereParams, sortParams);
    }

    @Override
    public Shelf add(Shelf shelf) {
        Map<String, Object> whereParams = new HashMap<>();
        shelf.createShelfCode();
        whereParams.put("shelfCode", shelf.getShelfCode());
        List<Shelf> ls = this.shelfDao.getEntitys(null, whereParams, null);
        if (ls != null && ls.size() > 0) {
            throw new AppRuntimeException("福位架编号不能重复");
        }
        initShelf(shelf);
        return shelf;
    }

    @Override
    public void update(Shelf shelf) {
        Shelf oldShelf = this.shelfDao.getEntityById(shelf.getShelfId());
        if (shelf.getShelfRow() < oldShelf.getShelfRow()) {
            throw new RuntimeException("行数不能小于当前的行数：" + oldShelf.getShelfRow());
        }
        if (shelf.getShelfColumn() < oldShelf.getShelfColumn()) {
            throw new RuntimeException("列数不能小于当前的行数："
                    + oldShelf.getShelfColumn());
        }
        shelf.setShelfArea(this.areaService.getById(shelf.getShelfArea().getAreaId()));
        shelf.createShelfCode();
        if (!shelf.getShelfCode().equals(oldShelf.getShelfCode())) {
            Map<String, Object> whereParams = new HashMap<String, Object>();
            whereParams.put("shelfCode", shelf.getShelfCode());
            List<Shelf> list = this.shelfDao
                    .getEntitys(null, whereParams, null);
            if (list != null && list.size() > 0) {
                throw new RuntimeException("所在区域行、列已有福位架，请勿重复添加！");
            }
        }
        this.shelfDao.updateEntity(shelf);
        for (int i = oldShelf.getShelfRow(); i < shelf.getShelfRow(); i++) {
            for (int j = oldShelf.getShelfColumn(); j < shelf.getShelfColumn(); j++) {
                BlessSeat bs = new BlessSeat();
                bs.setShelfRow(i + 1);
                bs.setShelfColumn(j + 1);
                bs.setShelf(shelf);
                bs.createBsCode();
                this.blessSeatService.add(bs);
            }
        }
    }

    @Override
    public List<Area> getAreaList() {
        return this.areaService.getAllByParams(null, null, null);
    }

    @Override
    public List<Shelf> getAllByParams(Pager page,
                                      Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.shelfDao.getEntitys(page, whereParams, sortParams);
    }

    @Override
    public Shelf getById(String id) {
        return this.shelfDao.getEntityById(id);
    }

    @Override
    public void delete(Shelf t) {
        this.shelfDao.deletEntity(t);
    }

    @Override
    public void initShelf(Shelf shelf) {
        this.shelfDao.saveEntity(shelf);
        for (int i = 0; i < shelf.getShelfRow(); i++) {
            for (int j = 0; j < shelf.getShelfColumn(); j++) {
                BlessSeat bs = new BlessSeat();
                bs.setShelfRow(i + 1);
                bs.setShelfColumn(j + 1);
                bs.setShelf(shelf);
                bs.createBsCode();
                bs.setPermit(shelf.isPermit());
                this.blessSeatService.add(bs);
            }
        }
    }

    @Override
    public void updateShelfPermit(String[] shelfIds, boolean permit) {
        if (shelfIds == null)
            return;

        for (String shelfId : shelfIds) {
            Map<String, Object> valueParams = new HashMap<>();
            Map<String, Object> whereParams = new HashMap<>();
            whereParams.put("shelfId", shelfId);
            valueParams.put("permit", permit);
            this.shelfDao.executeUpate(valueParams, whereParams);

            this.blessSeatService.updatePermitByShelfId(shelfId, permit);
        }
    }

    @Override
    public Shelf addRow(Shelf shelf, int shelfRow, boolean b) {
        if (shelf.getShelfRow() >= shelfRow) {
            return shelf;
        }
        if (shelfRow - shelf.getShelfRow() > 1) {
            throw new AppRuntimeException("只能在现有行基础上添加行，不能夸行添加");
        }
        shelf.setShelfRow(shelfRow);
        this.shelfDao.updateEntity(shelf);
        for (int j = 0; j < shelf.getShelfColumn(); j++) {
            BlessSeat bs = new BlessSeat();
            bs.setShelfRow(shelf.getShelfRow());
            bs.setShelfColumn(j + 1);
            bs.setShelf(shelf);
            bs.createBsCode();
            bs.setPermit(b);
            System.out.println(bs.getBsCode());
            if (this.blessSeatService.getBlessSeatByBSCode(bs.getBsCode()) == null) {
                this.blessSeatService.add(bs);
            }
        }
        return shelf;
    }

    @Override
    public Shelf addColumn(Shelf shelf, int shelfColumn, boolean b) {
        if (shelf.getShelfColumn() >= shelfColumn) {
            return shelf;
        }
        if (shelfColumn - shelf.getShelfColumn() > 1) {
            throw new AppRuntimeException("只能在现有列基础上添加列，不能夸列添加");
        }
        shelf.setShelfColumn(shelfColumn);
        this.shelfDao.updateEntity(shelf);
        for (int j = 0; j < shelf.getShelfRow(); j++) {
            BlessSeat bs = new BlessSeat();
            bs.setShelfRow(j + 1);
            bs.setShelfColumn(shelf.getShelfRow());
            bs.setShelf(shelf);
            bs.createBsCode();
            bs.setPermit(b);
            System.out.println(bs.getBsCode());
            if (this.blessSeatService.getBlessSeatByBSCode(bs.getBsCode()) == null) {
                this.blessSeatService.add(bs);
            }
        }
        return shelf;
    }

    @Override
    public Shelf addByArea(Area area, int areaRow, int areaColumn) {
        area = this.areaService.getById(area.getAreaId());
        if (area.getAreaRow() < areaRow) {
            this.areaService.addRow(area, areaRow, false);
        }
        if (area.getAreaColumn() < areaColumn) {
            this.areaService.addColumn(area, areaColumn, false);
        }
        String shelfCode = Shelf.getShelfCodeByArea(area, areaRow, areaColumn);
        Map<String, Object> whereParams = new HashMap<>();

        whereParams.put("shelfCode", shelfCode);
        Shelf shelf = this.shelfDao.getEntitys(null, whereParams, null).get(0);
        if (shelf == null) {
            return null;
        }
        if (!shelf.isPermit()) {
            shelf.setPermit(true);
            this.shelfDao.updateEntity(shelf);
            this.blessSeatService.updatePermitByShelfId(shelf.getShelfId(), true);
        }
        return shelf;
    }

    @Override
    public void updateShelfCode(String shelfId, String shelfCode) {
        if (shelfId == null || shelfId.trim().equals("")) {
            throw new AppRuntimeException("更改福位架编号时系统出错，请联系管理员！");
        }
        if (shelfCode == null || shelfCode.trim().equals("")) {
            throw new AppRuntimeException("福位架编号不能为空！");
        }
        Map<String, Object> whereParams = new HashMap<>();
        whereParams.put("shelfId", new HqlNoEquals(shelfId));
        whereParams.put("shelfCode", shelfCode);
        List<Shelf> shelfs = getAllByParams(null, whereParams, null);
        if (shelfs.size() > 0) {
            throw new AppRuntimeException("福位架编号不能重复！");
        }

        whereParams.clear();
        whereParams.put("shelfId",shelfId);
        Map<String,Object> values = new HashMap<>();
        values.put("shelfCode",shelfCode);
        this.shelfDao.updateEntity(values,whereParams);
    }

}
