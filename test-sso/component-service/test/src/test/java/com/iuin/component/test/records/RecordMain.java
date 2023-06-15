package com.iuin.component.test.records;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class RecordMain {
    public static void main(String[] args) {
//        QPageTemplate qPageTemplate = QPageTemplate.pageTemplate;
//        QEnterpriseShopRule qEnterpriseShopRule = QEnterpriseShopRule.enterpriseShopRule;
//        QEnterpriseShopGroupRule qEnterpriseShopGroupRule = QEnterpriseShopGroupRule.enterpriseShopGroupRule;
//        QMemberShop qMemberShop = QMemberShop.memberShop;
//        QMemberShopGroup qMemberShopGroup = QMemberShopGroup.memberShopGroup;
//        QShopCategory qShopCategory = QShopCategory.shopCategory;
//        QPageTemplateShop qPageTemplateShop = QPageTemplateShop.pageTemplateShop;
//        JPAQuery<ShopVO> shopVOJPAQuery = jpaQueryFactory.selectDistinct(
//                        Projections.constructor(
//                                ShopVO.class, qMemberShop.id, qMemberShop.logo, qMemberShop.name, qMemberShop.memberId, qMemberShop.roleId, qMemberShop.createTime
//                        )
//                ).from(qMemberShop)
//                .leftJoin(qMemberShop.memberShopGroups, qMemberShopGroup)
//                .leftJoin(qEnterpriseShopGroupRule).on(qEnterpriseShopGroupRule.shopGroupId.eq(qMemberShopGroup.shopGroupId))
//                .leftJoin(qEnterpriseShopRule).on(qEnterpriseShopGroupRule.id.eq(qEnterpriseShopRule.enterpriseShopGroupRule.id))
//                .leftJoin(qPageTemplate).on(qEnterpriseShopRule.shopId.eq(qPageTemplate.shopId))
//                .leftJoin(qPageTemplateShop).on(qPageTemplateShop.templateId.eq(qPageTemplate.id))
//                .leftJoin(qMemberShop.memberShopCategories, qShopCategory)
//                .where(qPageTemplate.id.eq(dto.getTemplateId()), qMemberShop.status.eq(CommonBooleanEnum.YES.getCode()), qPageTemplateShop.shopIds.contains(qMemberShop.id), qShopCategory.enterpriseShopGroupRuleCategoryId.eq(dto.getCategoryId()))
//                .orderBy(qMemberShop.createTime.desc()).limit(dto.getPageSize()).offset(PageUtil.currentPageToOffset(dto.getCurrent(), dto.getPageSize()));
//        long fetchCount = shopVOJPAQuery.fetchCount();
//        List<ShopVO> shopVOList = shopVOJPAQuery.fetch();

//        return Wrapper.success(new PageData<>(shopVOPage.getTotalElements(), shopVOPage.getContent()));


//        select distinct ms.id, ms.logo, ms.name, ms.member_id, ms.role_id, ms.create_time from product_member_shop ms
//        left join product_member_shop_member_shop_group pmsmsg on ms.id = pmsmsg.member_shop_id
//        left join product_member_shop_group msg on pmsmsg.member_shop_group_id = msg.id
//        left join product_enterprise_shop_group_rule pesgr on pesgr.shop_group_id = msg.shop_group_id
//        left join product_enterprise_shop_rule pesr on pesgr.id = pesr.enterprise_shop_group_rule_id
//        left join product_page_template ppt on ppt.shop_id = pesr.shop_id
//        left join product_page_template_shop ppts on ppts.template_id = ppt.id
//        left join product_shop_category psc on ms.id = psc.member_shop_id
//        where ppt.id = 2
//        and NOT ppts.shop_ids @> ('[' || ms.id || ']')::jsonb
//        and ms.status = 1 and psc.enterprise_shop_group_rule_category_id = 1
//        order by ms.create_time desc;

    }
}
